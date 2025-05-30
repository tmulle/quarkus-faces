/*
 * The MIT License
 *
 * Copyright (c) 2009-2024 PrimeTek Informatics
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primefaces.showcase.view.data.timeline;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

@Named("limitTimelineRangeView")
@ViewScoped
@RegisterForReflection(serialization = true)
public class LimitTimelineRangeView implements Serializable {

    private TimelineModel<String, ?> model;

    private LocalDateTime min;
    private LocalDateTime max;
    private long zoomMin;
    private long zoomMax;

    @PostConstruct
    public void init() {
        model = new TimelineModel<>();

        model.add(TimelineEvent.<String>builder().data("First").startDate(LocalDate.of(2015, 5, 25)).build());
        model.add(TimelineEvent.<String>builder().data("Last").startDate(LocalDate.of(2015, 5, 26)).build());

        // lower limit of visible range
        min = LocalDate.of(2015, 1, 1).atStartOfDay();

        // upper limit of visible range
        max = LocalDate.of(2015, 12, 31).atStartOfDay();

        // one day in milliseconds for zoomMin
        zoomMin = 1000L * 60 * 60 * 24;

        // about three months in milliseconds for zoomMax
        zoomMax = 1000L * 60 * 60 * 24 * 31 * 3;
    }

    public TimelineModel<String, ?> getModel() {
        return model;
    }

    public LocalDateTime getMin() {
        return min;
    }

    public LocalDateTime getMax() {
        return max;
    }

    public long getZoomMin() {
        return zoomMin;
    }

    public long getZoomMax() {
        return zoomMax;
    }

}