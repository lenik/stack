package com.bee32.plover.html;

import java.util.List;
import java.util.Set;

import javax.free.Nullables;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.EnumAltRegistry;
import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.EnumUtil;
import com.bee32.plover.site.cfg.Relabel;
import com.googlecode.jatl.Html;

public class FormEntry
        implements IFormChild {

    String inputName;

    String label;
    String tooltip;

    boolean critical;
    boolean readOnly;
    boolean hidden;

    Object value;

    public FormEntry(String inputName, String labelTip, Object value) {
        while (true) {
            if (inputName.startsWith("-"))
                readOnly = true;
            else if (inputName.startsWith("!"))
                critical = true;
            else if (inputName.startsWith("."))
                hidden = true;
            else
                break;
            inputName = inputName.substring(1);
        }
        this.inputName = inputName;

        int labelColon = labelTip.indexOf(':');
        if (labelColon != -1) {
            label = labelTip.substring(0, labelColon);
            tooltip = labelTip.substring(labelColon + 1);
        } else {
            label = labelTip;
            tooltip = "";
        }

        this.value = value;
    }

    public void render(Html out) {
        HttpServletRequest request = ThreadHttpContext.getRequest();
        render(out, request);
    }

    @Override
    public void render(Html out, HttpServletRequest req) {
        if (hidden) {
            out.tr().td().colspan("3");
            String sval = value == null ? "" : value.toString();
            out.input().name(inputName).type("hidden").value(sval).end();
            out.end(2); // .tr.td
            return;
        }

        out.tr();
        out.th().classAttr("key" + (critical ? " critical" : "")).text(label).end();
        out.td().classAttr("value");

        Html input;
        if (value == null || value instanceof String || value instanceof Number) {
            input = out.input().name(inputName).type("text");
            String sval = value == null ? "" : value.toString();
            input.value(sval);

        } else if (value instanceof Location) {
            Location location = (Location) value;
            String _location = Locations.qualify(location);
            String href = location.resolveAbsolute(req);
            out.span();
            {
                input = out.input().name(inputName).type("text");
                String sval = value == null ? "" : _location;
                input.value(sval);
                out.end();

                if (href != null)
                    out.img().classAttr("icon").src(href).end();
            }

        } else if (value instanceof Boolean) {
            input = out.input().name(inputName).type("checkbox").value("1");
            Boolean bval = (Boolean) value;
            if (bval == Boolean.TRUE)
                input.checked("true");

        } else if (value instanceof Enum<?>) { // select<option> name() -> label.
            Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) value.getClass();
            boolean hasLabel = ILabelledEntry.class.isAssignableFrom(enumClass);

            Enum<?>[] candidates = EnumUtil.values(enumClass);

            input = out.select().name(inputName);
            for (Enum<?> candidate : candidates) {
                boolean selected = Nullables.equals(value, candidate);
                Html option = out.option().value(candidate.name());
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

            input = out.select().name(inputName);
            for (EnumAlt<?, ?> it : EnumAltRegistry.nameMap(enmClass).values()) {
                boolean selected = Nullables.equals(value, it);
                Html option = out.option().value(it.getName());
                if (selected)
                    option.selected("selected");
                if (hasLabel) {
                    String itLabel = ((ILabelledEntry) it).getEntryLabel();
                    option.text(itLabel);
                } else {
                    option.text(it.getName());
                }
                option.end();
            }

        } else if (value instanceof IMultiSelectionModel) {// checkbox*: index[] -> label[].
            IMultiSelectionModel<?> msm = (IMultiSelectionModel<?>) value;
            input = out.div().style("max-width: 20em"); // ul();
            Set<Integer> selectedIndexes = msm.getSelectedIndexes();
            List<?> candidates = msm.getCandidates();
            for (int index = 0; index < candidates.size(); index++) {
                boolean isSelected = selectedIndexes.contains(index);
                Relabel<?> it = (Relabel<?>) candidates.get(index);

                String itLabel;
                if (it instanceof ILabelledEntry)
                    itLabel = ((ILabelledEntry) it).getEntryLabel();
                else
                    itLabel = it.toString();
                if (itLabel == null || itLabel.isEmpty())
                    itLabel = "(" + it.getKey() + ")";

                // li().;
                out.checkbox(isSelected).name(inputName).value(String.valueOf(it.getKey())).text(itLabel);
                // .end();
            }
        } else {
            throw new UnsupportedOperationException("Unsupported field value type for simple-form: " + value);
        }

        if (readOnly)
            input.readonly("readonly");
        input.end();

        out.end(); // .td
        out.td().classAttr("comment").text(tooltip).end();
        out.end(); // .tr
    }

}
