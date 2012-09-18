package com.bee32.sem.inventory.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 根据物料名称生成物料代码
 *
 * 代码生成器（策略）
 */
public class CodeGenerator
        extends EnumAlt<Character, CodeGenerator> {

    private static final long serialVersionUID = 1L;

    public CodeGenerator(char value, String name) {
        super(value, name);
    }

    public static CodeGenerator forName(String name) {
        return forName(CodeGenerator.class, name);
    }

    public static Collection<CodeGenerator> values() {
        return values(CodeGenerator.class);
    }

    public static CodeGenerator forValue(Character value) {
        return forValue(CodeGenerator.class, value);
    }

    public static CodeGenerator forValue(char value) {
        return forValue(new Character(value));
    }

    /** 手动输入 */
    public static final CodeGenerator NONE = new CodeGenerator('N', "none");

    /** 生成 GUID 宇宙唯一代码 */
    public static final CodeGenerator GUID = new CodeGenerator('G', "guid");

}
