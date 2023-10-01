package com.anynote.note.datascope.annotation;

import java.lang.annotation.*;

/**
 * 笔记数据权限注解
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoteDataScopeInterceptor {

    /**
     * 知识库表别名
     * @return
     */
    public String knowledgeBaseAlias() default "n_knowledge_base";

    /**
     * 笔记表别名
     * @return
     */
    public String noteAlias() default "n_note";

    /**
     * 需要的权限
     * @return
     */
    public String reqPermissions() default "";

}
