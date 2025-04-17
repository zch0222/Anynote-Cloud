package com.anynote.note.controller;

import com.anynote.core.constant.Constants;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.model.dto.AddUserKnowledgeBasesDTO;
import com.anynote.note.api.model.dto.DeleteUserKnowledgeBaseDTO;
import com.anynote.note.api.model.dto.UserKnowledgeBaseUpdateDTO;
import com.anynote.note.model.bo.KnowledgeBaseImportUserParam;
import com.anynote.note.model.bo.UserKnowledgeBaseParam;
import com.anynote.note.model.dto.KnowledgeBaseImportUserVO;
import com.anynote.note.service.KnowledgeBaseService;
import com.anynote.note.service.UserKnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/manage/bases")
@Validated
public class KnowledgeBaseManageController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Resource
    private UserKnowledgeBaseService userKnowledgeBaseService;

    @PostMapping("import")
    public ResData<KnowledgeBaseImportUserVO> importUsers(@RequestParam("users") @NotNull(message = "用户表格不能为空") MultipartFile users,
                                                          @RequestParam("knowledgeBaseId") @NotNull(message = "知识库id不能为空") Long knowledgeBaseId) {
        KnowledgeBaseImportUserParam importUserParam = new KnowledgeBaseImportUserParam();
        importUserParam.setId(knowledgeBaseId);
        importUserParam.setExcel(users);
        return ResData.success(knowledgeBaseService.importKnowledgeBaseUser(importUserParam));
    }


    /**
     * 更新用户权限
     * @param userKnowledgeBaseUpdateDTO
     * @return
     */
    @PutMapping("userKnowledgeBases")
    public ResData<String> updateUserKnowledgeBase(@RequestBody @Validated UserKnowledgeBaseUpdateDTO userKnowledgeBaseUpdateDTO) {
        userKnowledgeBaseService.updateUserKnowledgeBase(UserKnowledgeBaseParam.UserKnowledgeBaseParamBuilder()
                .permissions(userKnowledgeBaseUpdateDTO.getPermissions())
                .userId(userKnowledgeBaseUpdateDTO.getUserId())
                .knowledgeBaseId(userKnowledgeBaseUpdateDTO.getKnowledgeBaseId())
                .build());
        return ResUtil.success(Constants.SUCCESS_RES);
    }

    /**
     * 添加用户
     * @return
     */
    @PostMapping("addUser")
    public ResData<String> addUser(@RequestBody @Validated AddUserKnowledgeBasesDTO addUserKnowledgeBasesDTO) {
        userKnowledgeBaseService.addUserKnowledgeBase(UserKnowledgeBaseParam.UserKnowledgeBaseParamBuilder()
                        .knowledgeBaseId(addUserKnowledgeBasesDTO.getKnowledgeBaseId())
                        .userId(addUserKnowledgeBasesDTO.getUserId())
                        .permissions(addUserKnowledgeBasesDTO.getPermissions())
                .build());
        return ResUtil.success(Constants.SUCCESS_RES);
    }

    /**
     * 移除用户
     * @param deleteUserKnowledgeBaseDTO
     * @return
     */
    @PostMapping("deleteUser")
    public ResData<String> deleteUser(@RequestBody @Validated DeleteUserKnowledgeBaseDTO deleteUserKnowledgeBaseDTO) {
        userKnowledgeBaseService.deleteUserKnowledgeBase(UserKnowledgeBaseParam.UserKnowledgeBaseParamBuilder()
                .userId(deleteUserKnowledgeBaseDTO.getUserId())
                .knowledgeBaseId(deleteUserKnowledgeBaseDTO.getKnowledgeBaseId())
                .build());
        return ResUtil.success(Constants.SUCCESS_RES);
    }
}
