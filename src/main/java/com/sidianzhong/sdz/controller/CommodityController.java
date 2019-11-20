package com.sidianzhong.sdz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sidianzhong.sdz.annotation.SellerLoginToken;
import com.sidianzhong.sdz.annotation.UserLoginToken;
import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.service.*;
import com.sidianzhong.sdz.utils.PageInfo;
import com.sidianzhong.sdz.utils.ResultModel;
import com.sidianzhong.sdz.utils.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hxg on 2016/10/06.
 */
@Api(description = "商品")
@Controller
public class CommodityController {

    @Autowired
    CommodityService commodityService;
    @Autowired
    TwoClassifyService twoClassifyService;
    @Autowired
    ThreeClassifyService threeClassifyService;
    @Autowired
    CommodityClassService commodityClassService;
    @Autowired
    CommodityPostService commodityPostService;
    @Autowired
    ShopUserService shopUserService;
    @Autowired
    UserRoleService userRoleService;

    @Autowired
    HttpServletResponse response;

    @SellerLoginToken
    @ApiOperation(value = "创建'商品'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/commodities/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createCommodity(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "details") String details,
            @RequestParam(value = "price") String price,
            @RequestParam(value = "imgs") String[] imgs,
            @RequestParam(value = "params") Integer[] params,
            @RequestParam(value = "status", required = false, defaultValue = "0") Integer status
    ) {
        if (params == null || params.length == 0) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        Commodity item = new Commodity();
        Date date = new Date();
        item.setName(name);
        item.setUserId((Integer) request.getAttribute("USER_ID"));
        item.setPrice(price);
        item.setImg(JSONArray.toJSONString(imgs));
        item.setDetails(details);
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (commodityService.create(item) == 0) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        int commodityId = item.getId();

        List<CommodityClass> list = new ArrayList<>();
        for (Integer param : params) {
            CommodityClass commodityClass = new CommodityClass();
            commodityClass.setCommodityId(commodityId);
            commodityClass.setClassId(param);
            commodityClass.setStatus(0);
            commodityClass.setCreateTime(date);
            commodityClass.setLastUpdateTime(date);
            list.add(commodityClass);
        }

        if (commodityClassService.createList(list) == 0) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @SellerLoginToken
    @ApiOperation(value = "删除'商品'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/commodities/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteCommodity(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "commodity_id") Integer id
    ) {
        Commodity item = commodityService.get(id);
        if (null == item || commodityService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @SellerLoginToken
    @ApiOperation(value = "修改'商品'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/commodities/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editCommodity(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "img", required = false) String img,
            @RequestParam(value = "details", required = false) String details,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Commodity item = commodityService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (name != null) {
            item.setName(name);
        }
        if (price != null) {
            item.setPrice(price);
        }
        if (img != null) {
            item.setImg(img);
        }
        if (details != null) {
            item.setDetails(details);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (commodityService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'商品'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getCommodityById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodityById(
            @RequestParam(value = "id") Integer id
    ) {
        Commodity item = commodityService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'商品'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/commodities",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodities(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "img", required = false) String img,
            @RequestParam(value = "details", required = false) String details,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Commodity item = new Commodity();
        if (name != null) {
            item.setName(name);
        }
        if (price != null) {
            item.setPrice(price);
        }
        if (img != null) {
            item.setImg(img);
        }
        if (details != null) {
            item.setDetails(details);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<Commodity> list = commodityService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);

    }

    @ApiOperation(value = "获取商品列表")
    @RequestMapping(value = "/getCommodityList",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodityList(
            @RequestParam(value = "parentId") Integer oneParentId,
            @RequestParam(value = "threeParentId", required = false) Integer[] threeParentId
    ) {
        //获取出所在的二级列表全部的分类
        List<TwoClassify> twoClassifies = twoClassifyService.getListByParentId(oneParentId);
        List<Integer> twoClassId = twoClassifies.stream().map(twoClassify -> {
            return twoClassify.getId();
        }).collect(Collectors.toList());

        //获取到所在的三级列表分类
        List<ThreeClassify> threeClassify = threeClassifyService.getListByParentId(twoClassId);
        List<Integer> threeClassId = threeClassify.stream().map(threeClass -> {
            if (threeParentId == null) {
                return threeClass.getId();
            }
            for (Integer s : threeParentId) {
                if (threeClass.getId().equals(s)) {
                    return threeClass.getId();
                }
            }
            return null;
        }).collect(Collectors.toList());

        //获取到所在的商品分类
        List<CommodityClass> commodityClassList = commodityClassService.getListByClassId(threeClassId);
        List<Integer> collect = commodityClassList.stream().distinct().map(commodityClass -> {
            return commodityClass.getCommodityId();
        }).distinct().collect(Collectors.toList());

        List<Commodity> listByIds = commodityService.getListByIds(collect);
        for (int i = 0; i < listByIds.size(); i++) {
            Commodity commodity = listByIds.get(i);
            try {
                List<String> parse = (List<String>) JSONArray.parse(commodity.getImg());
                String commodityImg = parse.get(0);
                commodity.setImg(commodityImg);
            } catch (Exception ignored) {
            }
        }
        return new ResponseEntity<>(ResultModel.ok(listByIds), HttpStatus.OK);
    }

    @ApiOperation(value = "获取商品详情")
    @RequestMapping(value = "/getShopDetail",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodityList(
            @RequestParam(value = "commodityId") Integer commodityId
    ) {
        Commodity commodity = commodityService.get(commodityId);

        //获取到商品的参数绑定列表
        ArrayList<Integer> list = new ArrayList<>();
        list.add(commodityId);
        List<CommodityClass> listByCommodityId = commodityClassService.getListByCommodityId(list);
        List<Integer> classIds = listByCommodityId.stream().distinct().map(commodityClass -> {
            return commodityClass.getClassId();
        }).distinct().collect(Collectors.toList());

        //获取到该商品的分类信息
        List<ThreeClassify> threeClassify = threeClassifyService.getListById(classIds);
        List<Integer> threeClassIds = threeClassify.stream().distinct().map(commodityClass -> {
            return commodityClass.getParentId();
        }).distinct().collect(Collectors.toList());

        //获取到该商品的参数key
        List<TwoClassify> listByParentId = twoClassifyService.getListById(threeClassIds);

        //转换成map格式
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        for (int i = 0; i < listByParentId.size(); i++) {
            ThreeClassify threeClassify1 = threeClassify.get(i);
            TwoClassify twoClassify = listByParentId.get(i);
            jsonObject1.put(twoClassify.getName(), threeClassify1.getName());
        }

        //获取到邮寄方式
        String postPrice = null;
        List<CommodityPost> postByCommodityId = commodityPostService.getPostByCommodityId(commodityId);
        if (postByCommodityId.size() != 0) {
            CommodityPost commodityPost = postByCommodityId.get(0);
            String post = commodityPost.getPost();
            jsonObject1.put("post", post);
            postPrice = commodityPost.getPrice();
        }
        //获取商品价值详情
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("price", commodity.getPrice());
        jsonObject2.put("detail", commodity.getDetails());
        jsonObject2.put("name", commodity.getName());
        jsonObject2.put("postPrice", postPrice);

        //获取商品发布人信息
        ShopUser shopUser = shopUserService.get(commodity.getUserId());
        jsonObject2.put("userName", shopUser.getName());
        jsonObject2.put("userId", commodity.getUserId());

        List<UserRole> userByUserId = userRoleService.getUserByUserId(commodity.getUserId());
        for (UserRole userRole : userByUserId) {
            if (userRole.getRoleId() == 2) {
                String profile = userRole.getProfile();
                jsonObject2.put("profile", profile);
            }
        }

        //获取banner图片
        String img = commodity.getImg();
        Object parse = JSONArray.parse(img);
        jsonObject2.put("banner", parse);

        jsonObject.put("info", jsonObject2);
        jsonObject.put("param", jsonObject1);
        return new ResponseEntity<>(ResultModel.ok(jsonObject), HttpStatus.OK);
    }
}
