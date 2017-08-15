package com.midea.fridge.utils.fridgecontrolutil;

/**
 * 冰箱故障信息
 * Created by Administrator on 2016/9/6.
 */
public class FridgeFault {
    /**冷藏室开门超时*/
    public int lcdoor_timeout;
    /**冷冻室开门超时*/
    public int lddoor_timeout;
    /**吧台室开门超时*/
    public int bardoor_timeout;
    /**变温室开门超时*/
    public int bwdoor_timeout;
    /**制冰机满冰提示*/
    public int ice_toomuch;
    /**冷藏传感器故障*/
    public int lc_sensor_err;
    /**冷藏化霜传感器故障*/
    public int lchs_sensor_err;
    /**环温传感器故障*/
    public int hw_sensor_err;
    /**变温室传感器故障*/
    public int bw_sensor_err;
    /**右变温室传感器故障*/
    public int rightbw_sensor_err;
    /**冷冻室高温报警*/
    public int ld_high_temp_err;
    /**冷冻室传感器故障*/
    public int ld_sensor_err;
    /**冷冻室化霜传感器故障*/
    public int ldhs_sensor_err;
    /**制冰电机机械故障*/
    public int icemake_machine_err;
    /**冷藏室化霜超时故障*/
    public int lchs_timeout;
    /**冷冻室化霜超时故障*/
    public int ldhs_timeout;
    /**过零检查故障*/
    public int zero_check_err;
    /**EEPROM读写故障*/
    public int eeprom_err;
    /**左变温室传感器故障*/
    public int leftbw_sensor_err;
    /**冰镇室传感器故障*/
    public int ice_room_sensor_err;
    /**主显板通信故障*/
    public int communication_err;
    /**制冰机温度传感器故障*/
    public int icemake_sensor_err;
    /**变温化霜传感器故障*/
    public int bwhs_sensor1_err;
    /**变温化霜传感器2故障*/
    public int bwhs_sensor2_err;
    /**酸奶机传感器故障*/
    public int yoghourt_sensor_err;
    /**制冰机微动开关故障*/
    public int icemake_microswitch_err;
    /**制冰机进水管过滤器超时提醒*/
    public int icemake_waterinlet_err;
    /**环境湿度传感器故障*/
    public int ambient_humidity_sensor_err;
    /**冷藏室湿度传感器故障*/
    public int lc_humidity_sensor_err;
    /**雷达传感器1故障*/
    public int radar_sensor1_err;
    /**雷达传感器2故障*/
    public int radar_sensor2_err;
    /**雷达传感器3故障*/
    public int radar_sensor3_err;
    /**雷达传感器4故障*/
    public int radar_sensor4_err;
    /**雷达传感器5故障*/
    public int radar_sensor5_err;
    /**冷藏室超温提示*/
    public int lc_overheat_err;
    /**冷藏室低温提示*/
    public int lc_subzero_err;

    @Override
    public String toString() {
        return "FridgeFault{" +
                "lcdoor_timeout=" + lcdoor_timeout +
                ", lddoor_timeout=" + lddoor_timeout +
                ", bardoor_timeout=" + bardoor_timeout +
                ", bwdoor_timeout=" + bwdoor_timeout +
                '}';
    }
}
