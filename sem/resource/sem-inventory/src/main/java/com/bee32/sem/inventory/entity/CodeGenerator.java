package com.bee32.sem.inventory.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

/**
 * 代码生成器（策略）
 */
public class CodeGenerator
        extends EnumAlt<Character, CodeGenerator> {

    private static final long serialVersionUID = 1L;

    static final Map<String, CodeGenerator> nameMap = getNameMap(CodeGenerator.class);
    static final Map<Character, CodeGenerator> valueMap = getValueMap(CodeGenerator.class);

    public CodeGenerator(char value, String name) {
        super(value, name);
    }

    public static CodeGenerator forName(String altName) {
        CodeGenerator CodeGenerator = nameMap.get(altName);
        if (CodeGenerator == null)
            throw new NoSuchEnumException(CodeGenerator.class, altName);
        return CodeGenerator;
    }

    public static Collection<CodeGenerator> values() {
        Collection<CodeGenerator> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static CodeGenerator valueOf(Character value) {
        if (value == null)
            return null;
        return valueOf(value.charValue());
    }

    public static CodeGenerator valueOf(char value) {
        CodeGenerator CodeGenerator = valueMap.get(value);
        if (CodeGenerator == null)
            throw new NoSuchEnumException(CodeGenerator.class, value);
        return CodeGenerator;
    }

    /** 手动输入 */
    public static final CodeGenerator NONE = new CodeGenerator('N', "none");

    /** 生成 GUID 宇宙唯一代码 */
    public static final CodeGenerator GUID = new CodeGenerator('G', "guid");

}
