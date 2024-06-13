package pl.jkkk.cps.view.model;

import javafx.scene.control.TabPane;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CustomTabPane extends TabPane {

    /*------------------------ FIELDS REGION ------------------------*/
    private CustomTab chartTab;
    private CustomTab paramsTab;

    /*------------------------ METHODS REGION ------------------------*/
    public CustomTabPane(CustomTab chartTab,
                         CustomTab paramsTab) {
        super(chartTab, paramsTab);
        this.chartTab = chartTab;
        this.paramsTab = paramsTab;
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
    