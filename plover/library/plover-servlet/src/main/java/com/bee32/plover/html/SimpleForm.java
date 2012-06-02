package com.bee32.plover.html;

import java.io.Writer;
import java.util.List;
import java.util.Set;

import javax.free.Nullables;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.EnumAltRegistry;
import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;
import com.bee32.plover.site.EnumUtil;
import com.googlecode.jatl.Html;

public class SimpleForm
        extends HtmlSugar {

    public SimpleForm() {
        super();
    }

    public SimpleForm(Writer writer) {
        super(writer);
    }

    /**
     * array: each entry as { name, label, value }
     *
     * action may be in format "href:action-text"
     *
     * name may be prefixed with `-` for read-only fields.
     *
     * label may be in format "LABEL:HELPDOC", where HELPDOC is shown as control title.
     */
    public void put(String action, Object... array) {
        String actionText = "保存";
        int actionColon = action.indexOf(':');
        if (actionColon != -1) {
            actionText = action.substring(actionColon + 1);
            action = action.substring(0, actionColon);
        }

        form().method("get").action(action);
        {
            table().classAttr("tcf").border("0");
            for (int i = 0; i < array.length; i += 3) {
                String name = (String) array[i];
                boolean critical = false;
                boolean readOnly = false;
                boolean hidden = false;

                while (true) {
                    if (name.startsWith("-"))
                        readOnly = true;
                    else if (name.startsWith("!"))
                        critical = true;
                    else if (name.startsWith("."))
                        hidden = true;
                    else
                        break;
                    name = name.substring(1);
                }

                String label = (String) array[i + 1];
                String tooltip = "";
                int labelColon = label.indexOf(':');
                if (labelColon != -1) {
                    tooltip = label.substring(labelColon + 1);
                    label = label.substring(0, labelColon);
                }

                Object value = array[i + 2];

                if (hidden) {
                    tr().td().colspan("3");
                    String sval = value == null ? "" : value.toString();
                    input().name(name).type("hidden").value(sval).end();
                    end(2); // .tr.td
                    continue;
                }

                tr();
                th().classAttr("key" + (critical ? " critical" : "")).text(label).end();
                td().classAttr("value");

                Html input;
                if (value == null || value instanceof String || value instanceof Number) {
                    input = input().name(name).type("text");
                    String sval = value == null ? "" : value.toString();
                    input.value(sval);

                } else if (value instanceof Location) {
                    Location location = (Location) value;
                    String _location = Locations.qualify(location);
                    String href = location.resolveAbsolute(getRequest());
                    span();
                    {
                        input = input().name(name).type("text");
                        String sval = value == null ? "" : _location;
                        input.value(sval);
                        end();

                        if (href != null)
                            img().classAttr("icon").src(href).end();
                    }

                } else if (value instanceof Boolean) {
                    input = input().name(name).type("checkbox").value("1");
                    Boolean bval = (Boolean) value;
                    if (bval)
                        input.selected("selected");

                } else if (value instanceof Enum<?>) { // select<option> name() -> label.
                    Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) value.getClass();
                    boolean hasLabel = ILabelledEntry.class.isAssignableFrom(enumClass);

                    Enum<?>[] candidates = EnumUtil.values(enumClass);

                    input = select().name(name);
                    for (Enum<?> candidate : candidates) {
                        boolean selected = Nullables.equals(value, candidate);
                        Html option = option().value(candidate.name());
                        if (selected)
                            option.selected("selected");
                        if (hasLabel) {
                            String candidateLabel = ((ILabelledEntry) candidate).getEntryLabel();
                            option.text(candidateLabel);
                        } else {
                            option.text(candidate.name());
                        }
                        option.end();
                    }

                } else if (value instanceof EnumAlt<?, ?>) { // select<option> name -> label.
                    Class<? extends EnumAlt<?, ?>> enmClass = (Class<? extends EnumAlt<?, ?>>) value.getClass();
                    boolean hasLabel = ILabelledEntry.class.isAssignableFrom(enmClass);

                    input = select().name(name);
                    for (EnumAlt<?, ?> candidate : EnumAltRegistry.getNameMap(enmClass).values()) {
                        boolean selected = Nullables.equals(value, candidate);
                        Html option = option().value(candidate.getName());
                        if (selected)
                            option.selected("selected");
                        if (hasLabel) {
                            String candidateLabel = ((ILabelledEntry) candidate).getEntryLabel();
                            option.text(candidateLabel);
                        } else {
                            option.text(candidate.getName());
                        }
                        option.end();
                    }

                } else if (value instanceof IMultiSelectionModel) {// checkbox*: index[] -> label[].
                    IMultiSelectionModel<?> msm = (IMultiSelectionModel<?>) value;
                    input = div();
                    Set<Integer> indexes = msm.getIndexes();
                    List<?> candidates = msm.getCandidates();
                    for (int index = 0; index < candidates.size(); index++) {
                        boolean selected = indexes.contains(index);
                        Object candidate = candidates.get(index);
                        String candidateLabel;
                        if (candidate instanceof ILabelledEntry)
                            candidateLabel = ((ILabelledEntry) candidate).getEntryLabel();
                        else
                            candidateLabel = candidate.toString();
                        checkbox(selected).name(name).text(candidateLabel).end();
                    }
                } else {
                    throw new UnsupportedOperationException("Unsupported field value type for simple-form: " + value);
                }

                if (readOnly)
                    input.readonly("readonly");
                input.end();

                end(); // .td
                td().classAttr("comment").text(tooltip).end();
                end(); // .tr
            }
            tr().td().colspan("3").align("left");
            input().type("submit").name("save").value(actionText).end();
            text("");
            input().type("reset").value("重置").end();
            end(3); // .table.tr.td
        }
        end();
    }

}
