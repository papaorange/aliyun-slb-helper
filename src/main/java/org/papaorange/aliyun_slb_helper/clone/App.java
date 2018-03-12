package org.papaorange.aliyun_slb_helper.clone;

import java.io.IOException;
import java.util.List;
import org.papaorange.aliyun_slb_helper.api.SLBHelperAPI;

public class App {
  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println(
          "\n参数提示：\nexport all  导出所有地域所有实例到slbs目录下;\nclone local filname 使用导出到本地的slb配置文件克隆一个实例\nclone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例；\nclone功能忽略原有实例的付费类型，一律生成按量付费实例");
      return;
    }
    if (args[0].equals("export")) {
      if (args.length > 1 && args[1].equals("all")) {
        List<String> regions = SLBHelperAPI.describeRegions();

        regions.forEach(region -> {
          System.out.println("Region:" + region);

          SLBHelperAPI.describeLoadBalancers(region).forEach(lb -> {
            try {
              System.out.println("正在导出" + region + "地域的实例:" + lb.getLoadBalancerId() + "到本地slbs目录下");

              Exporter.exportLoadBalancer(region, lb.getLoadBalancerId(), true);
            } catch (IOException e) {
              // TODO Auto-generated catch block
              System.out.println("错误：\n导出过程出错");
              e.printStackTrace();
            }
          });
        });
      } else {

        System.out.println("提示：\nexport all导出所有地域所有实例到slbs目录下");
      }
      return;
    }

    if (args[0].equals("clone")) {
      if (!(args.length > 1)) {
        System.err.println(
            "提示：\nclone local filname 使用导出到本地的slb配置文件克隆一个实例\nclone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例；\nclone功能忽略原有实例的付费类型，一律生成按量付费实例");
      } else {
        switch (args[1]) {
          case "local":
            if (args[2] == null || args[2].trim().equals("")) {
              System.out.println(
                  "提示：\nclone local filname 使用导出到本地的slb配置文件克隆一个实例\nclone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例；\nclone功能忽略原有实例的付费类型，一律生成按量付费实例");
              return;
            }
            try {
              System.out.println("开始clone实例，新实例ID为：");
              Importer.importLoadbalancer(args[2], "", "");
            } catch (IOException e) {
              System.err.println("错误：\n打开文件出错，文件路径不正确？不是slb导出文件？" + "\"" + args[1] + "\"");
              e.printStackTrace();
            }
            break;
          case "online":
            if (args[2] == null || args[2].trim().equals("") || args[3] == null
                || args[3].trim().equals("")) {
              System.out.println("提示：\nclone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例");
              return;
            }
            try {
              String filename = Exporter.exportLoadBalancer(args[2], args[3], true);
              System.out.println("开始clone实例，新实例ID为：");
              Importer.importLoadbalancer(filename, "", "");
            } catch (IOException e) {
              // TODO Auto-generated catch block
              System.out.println(
                  "提示：\n输入的地域和实例名称不对？\nclone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例");
              e.printStackTrace();
            }


            break;
          default:
            System.err.println(
                "提示：\nclone local filname 使用导出到本地的slb配置文件克隆一个实例\nclone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例；\nclone功能忽略原有实例的付费类型，一律生成按量付费实例");

            break;
        }


      }
      return;
    }
    System.out.println(
        "\n\n参数提示：\nexport all  导出所有地域所有实例到slbs目录下;\nclone local \"filname\" 使用导出到本地的slb配置文件克隆一个实例，\nclone online \"region\" \"loadbalancerid\" 指定源实例所在地域和实例id，克隆一个新实例;\\nclone功能忽略原有实例的付费类型，一律生成按量付费实例");

  }
}
