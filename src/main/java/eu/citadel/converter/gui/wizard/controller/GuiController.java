package eu.citadel.converter.gui.wizard.controller;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.exceptions.DatasetException;
import eu.citadel.converter.gui.wizard.MainWindow;
import eu.citadel.converter.gui.wizard.MainWizard;
import eu.citadel.converter.gui.wizard.domain.ConfigFilePageFactory;
import eu.citadel.converter.gui.wizard.domain.Datatype;
import eu.citadel.converter.gui.wizard.domain.FileList;
import eu.citadel.converter.gui.wizard.domain.Status;
import eu.citadel.converter.gui.wizard.domain.UnitWork;
import eu.citadel.converter.gui.wizard.domain.UnitWorkList;
import eu.citadel.converter.gui.wizard.domain.Wizard;
import eu.citadel.converter.gui.wizard.domain.WizardPage;
import eu.citadel.converter.gui.wizard.exceptions.WizardCreationException;
import eu.citadel.converter.gui.wizard.localization.Messages;
import eu.citadel.converter.gui.wizard.pages.ExportSchemaPage;
import eu.citadel.converter.gui.wizard.pages.LoadFilesPage;
import eu.citadel.converter.gui.wizard.pages.ResultPreviewPage;
import eu.citadel.converter.gui.wizard.pages.SelectFilePage;
import eu.citadel.converter.gui.wizard.pages.SemanticMatchPage;
import eu.citadel.converter.gui.wizard.utility.GuiUtility;

public class GuiController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(GuiController.class);
	
	private static GuiController instance;	
	private static Locale LOCALE = Locale.getDefault();
	
	private UnitWorkList uwList;	
	private MainWindow mainWdv;
	private Wizard currentWizard;
	private UnitWork currentUnitWork;
	
	public final static Locale DEFAULT_LOCALE = Locale.getDefault();
	public FileList files = new FileList();	
	
	public static GuiController getInstance() {
		logger.trace("getInstance() - start");

		if (instance == null) {
			instance = new GuiController();		
		}

		logger.trace("getInstance() - end");
		return instance;
	}
	
	private GuiController() {
		logger.trace("GuiController() - start");

		uwList = new UnitWorkList();

		logger.trace("GuiController() - end");
	}
	
	public static void main(String[] args) {
		logger.trace("main(String[]) - start");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				logger.trace("$Runnable.run() - start");

				try {
					GuiController.getInstance().createGui();
				} catch (Exception e) {
					logger.warn("$Runnable.run()", e);

					e.printStackTrace();
				}

				logger.trace("$Runnable.run() - end");
			}
		});

		logger.trace("main(String[]) - end");
	}

	private void createGui() {
		logger.trace("createGui() - start");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				logger.trace("$Runnable.run() - start");

				try {
					mainWdv = new MainWindow();			
					logger.info("Citadel started");
				} catch (Exception e) {
					logger.warn("$Runnable.run()", e);

					e.printStackTrace();
				}

				logger.trace("$Runnable.run() - end");
			}
		});

		logger.trace("createGui() - end");
	}
	
	public <T extends Wizard> void startWizard(Class<T> w) throws IllegalArgumentException, InstantiationException, IllegalAccessException, WizardCreationException, InvocationTargetException {
		logger.trace("startWizard(Class<T>) - start");

		this.startWizard(w, null);

		logger.trace("startWizard(Class<T>) - end");
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Wizard> void startWizard(Class<T> w, Object[] params) throws InstantiationException, IllegalAccessException, WizardCreationException, IllegalArgumentException, InvocationTargetException {
		logger.trace("startWizard(Class<T>, Object[]) - start");

		if (w.equals(MainWizard.class)) {
			UnitWork uw = new UnitWork();
			uwList.add(uw);
			currentUnitWork = uw;
			mainWdv.setStatusBar(Messages.getString("Message.statusbar.open", "Main wizard open"));
			int nparams = 0;
			if (params != null) {
				nparams = params.length;
			}
			Constructor<?>[] ctors = w.getConstructors();
			for (Constructor<?> ctor : ctors) {
				Type[] ctorParams = ctor.getGenericParameterTypes();
				if (ctorParams.length == nparams) {
					currentWizard = (T)ctor.newInstance(params);

					logger.trace("startWizard(Class<T>, Object[]) - end");
					return;
				}
			}
			throw new WizardCreationException("Params number not match constructor");
		}

		logger.trace("startWizard(Class<T>, Object[]) - end");
	}

	public void exit() {
		logger.trace("exit() - start");

		logger.info("Citadel exit");
		System.exit(0);

		logger.trace("exit() - end");
	}

	public <T extends Wizard> boolean abort(T w) {
		logger.trace("abort(T) - start");

		if (w instanceof MainWizard) {
			files.undo();
			uwList.remove(currentUnitWork);
			currentUnitWork = null;			
		}
		closingWizard(w);

		logger.trace("abort(T) - end");
		return true;
	}
	
	public <T extends Wizard> boolean finish(T w) {
		logger.trace("finish(T) - start");

		if (w instanceof MainWizard) {
			files.commit();			
			logger.info("finish(T) - File list saved");
		}
		closingWizard(w);

		logger.trace("finish(T) - end");
		return true;
	}
	
	private void closingWizard(Wizard w) {
		logger.trace("closingWizard(Wizard) - start");

		mainWdv.setStatusBar(w.getTitle() + " close");

		logger.trace("closingWizard(Wizard) - end");
	}

	public <T extends Wizard> boolean nextPage(T wizard, WizardPage currentPage) {
		logger.trace("nextPage(T, WizardPage) - start");

		if (wizard instanceof MainWizard){
			if (currentPage instanceof LoadFilesPage) {
				if (files.size() == 0) {
					logger.warn("nextPage(T, WizardPage) - no file selected in {}",currentPage.getClass().getName());
					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), Messages.getString("Error.noFileSelected", "No dataset selected"), JOptionPane.ERROR_MESSAGE);

					logger.trace("nextPage(T, WizardPage) - end");
					return false;
				}
				logger.debug("Loaded files: {}", files.toString());
				logger.trace("nextPage(T, WizardPage) - end");
				return true;
			} else if (currentPage instanceof SelectFilePage) {
				if (currentUnitWork.getTargetFileValue() == null) {
					logger.warn("nextPage(T, WizardPage) - no file selected in {}",currentPage.getClass().getName());
					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), Messages.getString("Error.noFileSelected", "No dataset selected"), JOptionPane.ERROR_MESSAGE);

					logger.trace("nextPage(T, WizardPage) - end");
					return false;
				}				
				currentWizard.removePage(2);
				logger.info("nextPage(T, WizardPage) - deleted old page in {}",currentPage.getClass().getName());
				WizardPage page = null;
				try {
					page = ConfigFilePageFactory.buildPage(currentUnitWork.getTargetFileValue().getMetadata().getType(), 2, 2, wizard);
					logger.info("nextPage(T, WizardPage) - created page {}",page.getClass().getName());
				} catch (DatasetException | IOException e) {
					logger.warn("nextPage(T, WizardPage)", e);

					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), "Page file not founded", JOptionPane.ERROR_MESSAGE);
				}
				if (page== null) {
					logger.warn("nextPage(T, WizardPage) - file type not supported in {}",currentPage.getClass().getName());
					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), Messages.getString("Error.fileTypeNotSupported", "File type not supported"), JOptionPane.ERROR_MESSAGE);

					logger.trace("nextPage(T, WizardPage) - end");
					return false;
				}
				wizard.addPage(2, page);			
				logger.info("nextPage(T, WizardPage) - added page {} in {}",page.getClass().getName(), currentPage.getClass().getName());
				logger.trace("nextPage(T, WizardPage) - end");
				return true;
			} else if (currentPage instanceof SemanticMatchPage) {
				int notMatched;
				try {
					notMatched = GuiUtility.validSemanticMatch(currentUnitWork);
				} catch (DatasetException e) {
					logger.warn("nextPage(T, WizardPage)", e);

					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), Messages.getString("Error.invalidFormat", "Invalid format"), JOptionPane.ERROR_MESSAGE);

					logger.trace("nextPage(T, WizardPage) - end");
					return false;
				}
				if (notMatched > 0) {
					logger.warn("nextPage(T, WizardPage) - incomplete match in {}",currentPage.getClass().getName());
					int choise = GuiUtility.showConfirmationBox(Messages.getString("Warning.title","Warning"), notMatched+Messages.getString("Warning.incompleteSemanticMatch", " columns have no category assigned and they will be discarded. Do you want to continue?"), JOptionPane.YES_NO_OPTION);
					switch (choise) {
					case JOptionPane.NO_OPTION:
						logger.info("nextPage(T, WizardPage) - user not continue in {}",currentPage.getClass().getName());
						return false;
					case JOptionPane.YES_OPTION:
					default:
						logger.info("nextPage(T, WizardPage) - user continue in {}",currentPage.getClass().getName());
						return true;
					}
				}

				logger.trace("nextPage(T, WizardPage) - end");
				return true;			
			} else if (currentPage instanceof ExportSchemaPage) {
				Object[][] data = ((ExportSchemaPage) currentPage).getDataTable();
				int[] map = new int[data.length];
				for (int i = 0; i < data.length; i++) {
					map[((Datatype) data[i][1]).getId().getValue()] = i;
				}
				Status[] notMatched = GuiUtility.validSchemaMatch(currentUnitWork, map);
				int errors = 0;  
				for (Status s : notMatched) {
					if ((Status.Error).equals(s)) errors++;
				}
				if (errors > 0) {
					logger.warn("nextPage(T, WizardPage) - mandatory fields not matched in {}",currentPage.getClass().getName());
					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), Messages.getString("Error.pleaseResolveError", "Please resolve errors"), JOptionPane.ERROR_MESSAGE);
					((ExportSchemaPage) currentPage).setStatus(notMatched, map);

					logger.trace("nextPage(T, WizardPage) - end");
					return false;
				}
				((ExportSchemaPage) currentPage).setStatus(notMatched, map);
				currentUnitWork.mapIdToRowSchema = map;

				logger.trace("nextPage(T, WizardPage) - end");
				return true;
			} else if (currentPage instanceof ResultPreviewPage){
				try {
					currentUnitWork.saveFile(true);
				} catch (Exception e) {
					logger.warn("nextPage(T, WizardPage)", e);
					logger.warn("nextPage(T, WizardPage) - error saving file in {}",currentPage.getClass().getName());
					GuiUtility.showInfoBox(Messages.getString("Error.title","Error"), Messages.getString("Error.savingOutputFile", "Error saving to the provided path"), JOptionPane.ERROR_MESSAGE);

					logger.trace("nextPage(T, WizardPage) - end");
					return false;					
				}

				logger.trace("nextPage(T, WizardPage) - end");
				return true;
			}

			logger.trace("nextPage(T, WizardPage) - end");
			return true;
		}

		logger.trace("nextPage(T, WizardPage) - end");
		return false;
	}	

	public <T extends Wizard> boolean previousPage(T wizard, WizardPage currentPage) {
		logger.trace("previousPage(T, WizardPage) - start");
		logger.info("previousPage(T, WizardPage) - user go back from {}",currentPage.getClass().getName());
		logger.trace("previousPage(T, WizardPage) - end");
		return true;
	}	

	public void setCurrentWizard(Wizard wizard) {
		logger.trace("setCurrentWizard(Wizard) - start");

		this.currentWizard = wizard;
		logger.info("setCurrentWizard(Wizard) - current wizard is {}",wizard.getClass().getName());
		logger.trace("setCurrentWizard(Wizard) - end");
	}

	public UnitWork getCurrentUnitWork() {
		logger.trace("getCurrentUnitWork() - start");

		logger.trace("getCurrentUnitWork() - end");
		return currentUnitWork;
	}

	public static Locale getLocale() {
		logger.trace("getLocale() - start");

		logger.trace("getLocale() - end");
		return LOCALE;
	}

	public static void setLocale(Locale l) {
		logger.trace("setLocale(Locale) - start");

		Locale.setDefault(l);
		eu.citadel.converter.localization.Messages.MESSAGES_LOCALE = l;
		LOCALE = l;
		GuiUtility.initCategoriesContext();
		
		logger.info("setLocale(Locale) - current locale is {}",l.toString());
		logger.trace("setLocale(Locale) - end");
	}
}
