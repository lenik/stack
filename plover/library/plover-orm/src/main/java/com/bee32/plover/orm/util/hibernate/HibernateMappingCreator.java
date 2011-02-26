package com.bee32.plover.orm.util.hibernate;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HibernateMappingCreator {

    /**
     * To be generalized.
     */
    public static Document createMappingDocument(Class<?> clazz) {

        DOMDocumentType $documentType = new DOMDocumentType("hibernate-mapping", //
                "-//Hibernate/Hibernate Mapping DTD 3.0//EN", //
                "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");

        DOMDocument $document = new DOMDocument($documentType);

        Element $root = new DOMElement("hibernate-mapping");

        String packageName = clazz.getPackage().getName();
        String simpleName = clazz.getSimpleName();
        $root.setAttribute("package", packageName);
        $root.setAttribute("default-access", "field");

        DOMElement $class = new DOMElement("class");
        $class.addAttribute("name", simpleName);
        // classElm.addAttribute("table", simpleName);

        DOMElement $id = new DOMElement("id");
        $id.setAttribute("name", "id");
        // $id.setAttribute("column", "ID");
        $id.add(new DOMElement("generator").addAttribute("class", "native"));
        $class.add($id);

        DOMElement $property = new DOMElement("property");
        $class.add($property);

        $document.add($class);
        return $document;
    }

}
