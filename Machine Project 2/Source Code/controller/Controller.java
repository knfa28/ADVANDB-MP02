package controller;

import java.util.ArrayList;
import model.LocationModel;
import model.ModelBase;
import model.ModelCubeGen;
import model.ModelCubeSpec;
import model.ModelDrilldown1;
import model.ModelDrilldown2;
import model.ModelRollup;
import view.*;

public class Controller {
    private AppView appView;
    private static Controller firstInstance = null;
    
    private Controller() {
        appView = new AppView(this);
        updateLocationDetails();
    }
    
    public static Controller getInstance(){
        if(firstInstance == null)
            firstInstance = new Controller();       
        
        return firstInstance;
    }
    
    public void updateLocationDetails(){
        appView.updateLocationDetails(LocationModel.getMun(),
                                      LocationModel.getZone(),
                                      LocationModel.getBrgy(),
                                      LocationModel.getPurok());
    }
    
    public void updateHHTables(){
        appView.updateHHTables(ModelBase.getAll(),
                                ModelRollup.getAll(),
                                ModelDrilldown1.getAll(),
                                ModelDrilldown2.getAll());
    }
    
    public void sliceHHTables(boolean isCalam, ArrayList<String> cond){
        appView.updateHHTables(ModelBase.drillDown(isCalam, cond),
                                ModelRollup.drillDown(isCalam, cond),
                                ModelDrilldown1.drillDown(isCalam, cond),
                                ModelDrilldown2.drillDown(isCalam, cond));
    }
    
    public void updateCubeTables(){
        appView.updateCubeTables(ModelCubeGen.getAll(), ModelCubeSpec.getAll());
    }
}
