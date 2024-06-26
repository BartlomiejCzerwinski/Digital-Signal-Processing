package pl.bcpr.cps.view.model;

import javafx.scene.control.TabPane;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CustomTabPane extends TabPane {
    private CustomTab chartTab;
    private CustomTab histogramTab;
    private CustomTab paramsTab;
    private CustomTab tabW1;
    private CustomTab tabW2;

    public CustomTabPane(CustomTab chartTab,
                         CustomTab paramsTab, CustomTab tabW1, CustomTab tabW2) {
        super(chartTab, paramsTab, tabW1, tabW2);
        this.chartTab = chartTab;
        this.paramsTab = paramsTab;
        this.tabW1 = tabW1;
        this.tabW2 = tabW2;
    }

    public CustomTab getChartTab() {
        return chartTab;
    }

    public void setChartTab(CustomTab chartTab) {
        this.chartTab = chartTab;
    }

    public CustomTab getParamsTab() {
        return paramsTab;
    }

    public void setParamsTab(CustomTab paramsTab) {
        this.paramsTab = paramsTab;
    }

    public CustomTab getTabW1() {
        return tabW1;
    }

    public void setTabW1(CustomTab tabW1) {
        this.tabW1 = tabW1;
    }

    public CustomTab getTabW2() {
        return tabW2;
    }

    public void setTabW2(CustomTab tabW2) {
        this.tabW2 = tabW2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomTabPane that = (CustomTabPane) o;

        return new EqualsBuilder()
                .append(chartTab, that.chartTab)
                .append(paramsTab, that.paramsTab)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(chartTab)
                .append(paramsTab)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("chartTab", chartTab)
                .append("paramsTab", paramsTab)
                .toString();
    }
}
    