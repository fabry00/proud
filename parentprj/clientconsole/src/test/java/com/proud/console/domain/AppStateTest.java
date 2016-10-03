package com.proud.console.domain;

import com.proud.console.AppDataProviderMock;
import com.proud.console.Helper;
import com.proud.console.service.appservice.AppEventManager;
import com.proud.console.service.provider.IDataProvider;
import com.proud.domain.PredictionType;
import javafx.collections.ObservableList;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class AppStateTest {

    private static final Helper helper = new Helper();
    private static final IDataProvider dataProvider = new AppDataProviderMock();
    private static final AppState currentState = new AppState.Builder(null).build();

    public AppStateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        dataProvider.getSystemState(new ICallback() {
            @Override
            public void success(Object state) {
                currentState.copyFrom((AppState) state);
                currentState.setState(State.STARTED);
            }

            @Override
            public void fail(String message) {
                System.err.println(message);
            }
        });
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getState method, of class AppState.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        assertEquals(State.UNKWOWN, instance.getState());
    }

    /**
     * Test of getStateProp method, of class AppState.
     */
    @Test
    public void testGetStateProp() {
        System.out.println("getStateProp");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        assertEquals(State.UNKWOWN.getLabel(), instance.getStateProp().get());
    }

    /**
     * Test of getMessage method, of class AppState.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.setMessage("msg");
        assertEquals("msg", instance.getMessageProp().get());
    }

    /**
     * Test of getNodesInAbnormalState method, of class AppState.
     */
    @Test
    public void testGetNodesInAbnormalState() {
        System.out.println("getNodesInAbnormalState");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.addAbnormalNode(helper.getNode("Node"));
        ObservableList result = instance.getNodesInAbnormalState();
        assertTrue(result.size() == 1);
        Assert.assertEquals(helper.getNode("Node"), result.get(0));
    }

    /**
     * Test of getLayers method, of class AppState.
     */
    @Test
    public void testGetLayers() {
        System.out.println("getLayers");
        ObservableList<IAppElement> expResult = ((AppDataProviderMock) dataProvider).state.getLayers();
        ObservableList<IAppElement> result = currentState.getLayers();
        assertTrue(expResult.size() == result.size());
    }

    /**
     * Test of getFailurePrediction method, of class AppState.
     */
    @Test
    public void testGetFailurePrediction() {
        System.out.println("getFailurePrediction");
        Assert.assertEquals(PredictionType.NOT_DETECTED, ((AppDataProviderMock) dataProvider).state.getFailurePrediction());
    }

    /**
     * Test of getSystemFailure method, of class AppState.
     */
    @Test
    public void testGetSystemFailure() {
        System.out.println("getSystemFailure");
        Assert.assertEquals(PredictionType.NOT_DETECTED, ((AppDataProviderMock) dataProvider).state.getSystemFailure());
    }

    /**
     * Test of setState method, of class AppState.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.setState(State.ERROR);
        assertEquals(State.ERROR, instance.getState());
    }

    /**
     * Test of addNodeData method, of class AppState.
     */
    @Test
    public void testAddNodeData() {
        System.out.println("addNodeData");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.addNodeData(helper.getNode("Node"));
        Assert.assertEquals(helper.getNode("Node"), instance.getNodes().get(0));
    }

    /**
     * Test of addAbnormalNode method, of class AppState.
     */
    @Test
    public void testAddAbnormalNode() {
        System.out.println("addAbnormalNode");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.addAbnormalNode(helper.getNode("Node"));
        Assert.assertEquals(helper.getNode("Node"), instance.getNodesInAbnormalState().get(0));
    }

    /**
     * Test of addLayer method, of class AppState.
     */
    @Test
    public void testAddLayer() {
        System.out.println("addLayer");
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.addLayer(helper.getLayer("Layer"));
        Assert.assertEquals(helper.getLayer("Layer"), instance.getLayers().get(0));
    }

    /**
     * Test of clone method, of class AppState.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        currentState.setFailurePrediction(PredictionType.DETECTED);
        currentState.setSystemFailure(PredictionType.DETECTED);
        AppState instance = currentState.clone();

        assertEquals(instance.getState(), currentState.getState());
        assertEquals(instance.getFailurePrediction(), currentState.getFailurePrediction());
        assertEquals(instance.getSystemFailure(), currentState.getSystemFailure());
        assertEquals(instance.getLayers().size(), currentState.getLayers().size());
        assertEquals(instance.getMessageProp().get(), currentState.getMessageProp().get());
        assertEquals(instance.getNodes().size(), currentState.getNodes().size());
        assertEquals(instance.getNodesInAbnormalState().size(), currentState.getNodesInAbnormalState().size());
        assertEquals(instance.getStateProp().get(), currentState.getStateProp().get());
    }

    /**
     * Test of copyFrom method, of class AppState.
     */
    @Test
    public void testCopyFrom() {
        System.out.println("copyFrom");
        currentState.setFailurePrediction(PredictionType.DETECTED);
        currentState.setSystemFailure(PredictionType.DETECTED);
        AppState stateToCpy = currentState;
        AppState instance = new AppState.Builder(new AppEventManager()).build();
        instance.copyFrom(stateToCpy);

        assertEquals(instance.getState(), currentState.getState());
        assertEquals(instance.getFailurePrediction(), currentState.getFailurePrediction());
        assertEquals(instance.getSystemFailure(), currentState.getSystemFailure());
        assertEquals(instance.getLayers().size(), currentState.getLayers().size());
        assertEquals(instance.getMessageProp().get(), currentState.getMessageProp().get());
        assertEquals(instance.getNodes().size(), currentState.getNodes().size());
        assertEquals(instance.getNodesInAbnormalState().size(), currentState.getNodesInAbnormalState().size());
        assertEquals(instance.getStateProp().get(), currentState.getStateProp().get());
    }
}
