package borges.dimitrius.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ControllerCtx{

    String getCtxPath();

    Map<String,ControllerCtx> keyValDic = new HashMap<String, ControllerCtx>();

    static ControllerCtx findByValue(String value){
        return keyValDic.get(value);
    }

    static List<String> getAllCtxs(List<ControllerCtx> allVals){
        return allVals.stream().map(ControllerCtx::getCtxPath).collect(Collectors.toList());
    }

}
