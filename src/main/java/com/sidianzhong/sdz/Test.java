package com.sidianzhong.sdz;

import com.sidianzhong.sdz.pay.wxPay.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class Test {

    private static final String url = "http://192.168.31.96:8090";

    private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMyIsImlhdCI6MTU3MjU5NjI1MSwidG9rZW4iOiI3Y2Q1MWQ3OC0zZThkLTRiYmQtOTk0YS1kMzc1YzJiZWMwN2UifQ.NiUFwUeXaEx6rDMraNasCPPKmsec8MwmI3RqVqY3FIA";

    public static void main(String[] args) {
        for (int id = 131; id <= 186; id++) {
            int j = ((id - 180) % 4) * 2 + 1;
            for (int i = j; i <= j + 1; i++) {
                switch (i) {
                    case 1:
                        run(id, "题材");
                        break;
                    case 2:
                        run(id, "题材2");
                        break;
                    case 3:
                        run(id, "自描");
                        break;
                    case 4:
                        run(id, "泼墨");
                        break;
                    case 5:
                        run(id, "130cm  x  160cm");
                        break;
                    case 6:
                        run(id, "110cm  x  180cm");
                        break;
                    case 7:
                        run(id, "厕所");
                        break;
                    case 8:
                        run(id, "大门口");
                        break;
                }
            }
        }
    }

    private static void run(int id, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("parentId", id + "");

        Map<String, String> map1 = new HashMap<>();
        map1.put("X-Auth-Token", token);
        String json = HttpClientUtil.post(url + "/three_classifies/new", map1, map);

        try {
            System.out.println(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}


