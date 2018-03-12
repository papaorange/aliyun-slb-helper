package org.papaorange.aliyun_slb_helper.clone;

import java.io.IOException;
import java.util.List;
import org.papaorange.aliyun_slb_helper.api.SLBHelperAPI;

public class App {
  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println("参数：\n export all  导出所有地域所有实例到slbs目录下;\n import filname 导入指定lb");
      return;
    }
    if (args[0].equals("export")) {
      if (args.length > 1 && args[1].equals("all")) {
        List<String> regions = SLBHelperAPI.describeRegions();

        regions.forEach(region -> {
          System.out.println("Region:" + region);

          SLBHelperAPI.describeLoadBalancers(region).forEach(lb -> {
            try {
              Exporter.exportLoadBalancer(region, lb.getLoadBalancerId(), true);
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          });
        });
      } else {

        System.out.println("export all导出所有地域所有实例到slbs目录下");
      }
      return;
    }

    if (args[0].equals("import")) {
      if (!(args.length > 1)) {
        System.err.println("请输入要导入的lb文件路径");
      } else {
        try {
          Importer.importLoadbalancer(args[1], "", "");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
          System.err.println("找不到文件" + "\"" + args[1] + "\"");
        }
      }
      return;
    }
    System.out.println("参数：\n export all  导出所有地域所有实例到slbs目录下;\n import filname 导入指定lb");

  }
}
