package com.radien;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 16:31 2022/5/12
 * @Modified By:
 */
public class NacosServices {

    private NacosService[] serviceList;

    public NacosService[] getServiceList() {
        return serviceList;
    }

    public void setServiceList(NacosService[] serviceList) {
        this.serviceList = serviceList;
    }

    public static class NacosService{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
