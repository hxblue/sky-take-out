package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
     @Autowired
     private SetmealService setmealService;


     /**
      * 新增套餐
      * @param setmealDTO
      * @return
      */
     @PostMapping
     @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
     public Result save(@RequestBody SetmealDTO setmealDTO){
         setmealService.saveWithDish(setmealDTO);
         return Result.success();
     }

     /**
      * 分页查询套餐
      * @param setmealPageQueryDTO
      * @return
      */
     @GetMapping("/page")
     public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
          PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
          return Result.success(pageResult);
     }

     /**
      * 删除套餐
      * @param ids
      * @return
      */
     @DeleteMapping
     @CacheEvict(cacheNames = "setmealCache" , allEntries = true )
     public Result delete(@RequestParam List<Long> ids){

         setmealService.deleteBatch(ids);
         return Result.success();
     }

     @GetMapping("/{id}")
     public Result get(@PathVariable Long id){
         SetmealVO setmealVO = setmealService.getByIdWithDish(id);
         return Result.success(setmealVO);
     }

     @PutMapping
     @CacheEvict(cacheNames = "setmealCache" , allEntries = true )
     public Result update(@RequestBody SetmealDTO setmealDTO){
         setmealService.update(setmealDTO);
         return Result.success();
     }

     @PostMapping("/status/{status}")
     @CacheEvict(cacheNames = "setmealCache" , allEntries = true )
     public Result startOrStop(@PathVariable Integer  status, Long id){
            setmealService.startOrStop(status, id);
            return Result.success();
     }

}
