package dev.comgaming.framework.service;

import dev.comgaming.framework.Framework;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class Service {

    private String name;
    private String description;
    private boolean active;
    @Getter
    private boolean onlyAvivabelForTeam;
    private UUID uuid;
    @Getter
    public static int numberOfServices;

    public Service(String name, String description, boolean onlyAvivabelForTeam, boolean active){
        setName(name);
        setDescription(description);
        setOnlyAvivabelForTeam(onlyAvivabelForTeam);
        setActive(active);
        numberOfServices++;
    }

    public String toggleService(){
        if(uuid != null){
            if(this.isActive()){
                this.setActive(false);
                return STR."\{getName()} is now disabled";
                // uuid.toString() + " " + this.getName()
            }else {
                this.setActive(true);
                return STR."\{getName()} is now enabled";
            }
        }else{
            return "uuid is null";
        }
    }

    public void isActiveString(){
        if(this.isActive()){
            Framework.getLogger().info(STR."\{uuid.toString()} \{this.getName()}", "The Service " + getName() + " is  active");
        }else{
            Framework.getLogger().info(STR."\{uuid.toString()} \{this.getName()}", "The Service " + getName() + " is  inactive");
        }
    }

    public String isOnlyAvivabelForTeamString(){
        if(onlyAvivabelForTeam){
            return "Only avivabel for team";
        }else
            return "Only avivabel for team";
    }

}
