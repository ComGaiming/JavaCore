package dev.comgaming.framework.service;

import dev.comgaming.framework.Framework;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ServiceManager {

    public static ArrayList<Service> publicService = new ArrayList<>();
    public static ArrayList<Service> privateService = new ArrayList<>();
    Service service = new Service();

    public void addService(Service service){
        if(!service.isOnlyAvivabelForTeam())
            publicService.add(service);
        else
            privateService.add(service);
    }

    public void removeService(Service service){
        if(!service.isOnlyAvivabelForTeam())
            publicService.remove(service);
        else
            privateService.remove(service);
    }

    public void showServiceList(){
        Framework.getLogger().info("ServiceManager", "Public Service List");
        for(Service service : publicService){
            Framework.getLogger().info("ServiceManager", String.valueOf(service));
        }
        Framework.getLogger().info("ServiceManager", "Private Service List");
        for(Service service : privateService){
            Framework.getLogger().info("ServiceManager", String.valueOf(service));
        }
    }
}