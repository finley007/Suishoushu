package com.changyi.fi.external.enterprise.manager;

import com.changyi.fi.core.ConfigService;
import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.exception.UnknowEnterpriseAPIImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.qxb.QiXinBaoAPIServiceImpl;
import com.changyi.fi.external.enterprise.tyc.TianYanChaAPIServiceImpl;
import com.changyi.fi.core.model.SysServImplPO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnternalEnterpriseAPIManager {

    public static final String API_TIANYANCHA = "tyc";
    public static final String API_QIXINBAO = "qxb";

    private static final String SERV_IMPL_TYPE_ENTERPRISE = "enterprise";

    private static List<SysServImplPO> impls;

    private static Selector getSelector() {
        if (impls == null) {
            refresh();
        }
        return new Selector(impls);
    }

    public static ExternalEnterpriseAPIService getAPIImpl(String key) throws Exception {
        if (API_TIANYANCHA.equals(key)) {
            return new TianYanChaAPIServiceImpl();
        } if (API_QIXINBAO.equals(key)) {
            return new QiXinBaoAPIServiceImpl();
        } else {
            throw new UnknowEnterpriseAPIImpl("The enterprise service implements: " + key + "is unknown");
        }
    }

    public static void refresh() {
        ConfigService configService = CtxProvider.getContext().getBean(ConfigService.class);
        impls = configService.getSysServImplsByType(SERV_IMPL_TYPE_ENTERPRISE);
    }

    public static void disableAPI(String key) {
        if (impls == null) {
            refresh();
        }
        for (SysServImplPO po : impls) {
            if (po.getId().equals(key)) {
                impls.remove(po);
            }
        }
    }

    public static ExternalEnterpriseAPIService getAPIImpl() throws Exception {
        String key = getSelector().select();
        return getAPIImpl(key);
    }

    private static class Selector {

        private Map<Double, String> map;

        private Double[] arr;

        public Selector(List<SysServImplPO> list) {
            if (list != null && list.size() > 0) {
                map = new HashMap<Double, String>();
                arr = new Double[list.size()];
                int total = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getWeight() > 0) {
                        total += list.get(i).getWeight();
                        arr[i] = Double.valueOf(total);
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getWeight() > 0) {
                        arr[i] = arr[i] / total;
                        map.put(arr[i], list.get(i).getId());
                    }
                }
            }
        }

        public String select() {
            if (arr != null && map != null) {
                Double rand = Math.random();
                for (int i = 0; i < this.arr.length; i++) {
                    if (rand <= this.arr[i]) {
                        return this.map.get(this.arr[i]);
                    }
                }
            }
            return "";
        }


    }

}
