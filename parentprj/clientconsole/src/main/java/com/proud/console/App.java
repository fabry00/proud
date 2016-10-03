package com.proud.console;

import com.airhacks.afterburner.injection.Injector;
import com.proud.commons.AppHelper;
import com.proud.commons.ConfigUtils;
import com.proud.console.domain.ActionType;
import com.proud.console.domain.AppAction;
import com.proud.console.service.appservice.ApplicationService;
import com.proud.console.view.dashboard.DashboardPresenter;
import com.proud.console.view.dashboard.DashboardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author adam-bien.com
 */
public class App extends Application {

    private static final String APP_TITLE = "PrOud - Predicting Cloud Failures Online";
    private static final String APP_CSS = "app.css";
    private final AppHelper appHelper = new AppHelper();
    private final Logger logger = Logger.getLogger(App.class);
    private String actualTheme = "";
    private Properties appConfigs;
    private DashboardView appView;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public App() {
        appHelper.initLogger();
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.debug("start");
        try {
            actualTheme = STYLESHEET_CASPIAN;
            setUserAgentStylesheet(STYLESHEET_CASPIAN);

            initConfiguration();
            appHelper.forceTimeZone(appConfigs);
            initStage(stage);
        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
        logger.debug("started");
    }

    @Override
    public void stop() throws Exception {
        logger.debug("stop");
        Injector.forgetAll();

        DashboardPresenter presenter = appView.getRealPresenter();
        presenter.getAppService().dispatch(new AppAction<>(ActionType.CLOSE, null));

    }

    public void changeTheme() {
        actualTheme = (actualTheme.equals(STYLESHEET_CASPIAN))
                ? STYLESHEET_MODENA : STYLESHEET_CASPIAN;

        setUserAgentStylesheet(actualTheme);
    }

    public void switchToFullScreen(boolean b) {
        this.stage.setFullScreen(b);
    }

    public Properties getAppConfigs() {
        return appConfigs;
    }

    private void initStage(Stage stage) {
        logger.debug("initStage");
        ApplicationService.setMainApp(this);
        appView = new DashboardView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle(APP_TITLE);
        final String uri = getClass().getResource(APP_CSS).toExternalForm();
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();

        this.stage = stage;
    }

    private void initConfiguration() {
        logger.debug("initConfiguration");

        ConfigUtils utils = new ConfigUtils();
        appConfigs = utils.getInternalProp(App.class.getClassLoader(), ConfigUtils.APP_CONFIGS);
        /*
         * Properties of any type can be easily injected.
         */
        // LocalDate date = LocalDate.of(4242, Month.JULY, 21);

        //Map<Object, Object> context = new HashMap<>();
        //context.put("date", date);

        // This is needed to be able to Inject interfaces implementation
        // context.put("backendService", new ThreadBackendService(null));
        /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
        //System.setProperty("happyEnding", " Enjoy the flight!");
    }

}
