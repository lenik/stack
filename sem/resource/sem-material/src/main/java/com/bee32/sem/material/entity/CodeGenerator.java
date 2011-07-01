package com.bee32.sem.material.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class CodeGenerator
        extends EnumAlt<Character, CodeGenerator> {

    private static final long serialVersionUID = 1L;

    public CodeGenerator(Character value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, CodeGenerator> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, CodeGenerator> getValueMap() {
        return valueMap;
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

    public static CodeGenerator valueOf(String altName) {
        CodeGenerator CodeGenerator = nameMap.get(altName);
        if (CodeGenerator == null)
            throw new NoSuchEnumException(CodeGenerator.class, altName);
        return CodeGenerator;
    }

    static final Map<String, CodeGenerator> nameMap = new HashMap<String, CodeGenerator>();
    static final Map<Character, CodeGenerator> valueMap = new HashMap<Character, CodeGenerator>();

    public static final CodeGenerator NONE = new CodeGenerator('N', "none");
    public static final CodeGenerator GUID = new CodeGenerator('G', "guid");

}
