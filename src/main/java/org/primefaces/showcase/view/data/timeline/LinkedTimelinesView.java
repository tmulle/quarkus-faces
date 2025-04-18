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
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.primefaces.component.timeline.TimelineUpdater;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

@Named("linkedTimelinesView")
@ViewScoped
@RegisterForReflection(serialization = true)
public class LinkedTimelinesView implements Serializable {

    private TimelineModel<Task, ?> modelFirst;  // model of the first timeline
    private TimelineModel<String, ?> modelSecond; // model of the second timeline
    private boolean aSelected;         // flag if the project A is selected (for test of select() call on the 2. model)

    private LocalDateTime start = LocalDate.of(2015, 8, 22).atStartOfDay();
    private LocalDateTime end = LocalDate.of(2015, 9, 4).atStartOfDay();

    @PostConstruct
    public void init() {
        createFirstTimeline();
        createSecondTimeline();
    }

    private void createFirstTimeline() {
        modelFirst = new TimelineModel<>();

        modelFirst.add(TimelineEvent.<Task>builder()
                .data(new Task("Mail from boss", "images/timeline/mail.png", false))
                .startDate(LocalDateTime.of(2015, 8, 22, 17, 30))
                .build());
        modelFirst.add(TimelineEvent.<Task>builder()
                .data(new Task("Call back my boss", "images/timeline/callback.png", false))
                .startDate(LocalDateTime.of(2015, 8, 23, 23, 0))
                .build());
        modelFirst.add(TimelineEvent.<Task>builder()
                .data(new Task("Travel to Spain", "images/timeline/location.png", false))
                .startDate(LocalDateTime.of(2015, 8, 24, 21, 45))
                .build());
        modelFirst.add(TimelineEvent.<Task>builder()
                .data(new Task("Do homework", "images/timeline/homework.png", true))
                .startDate(LocalDate.of(2015, 8, 26))
                .endDate(LocalDate.of(2015, 9, 2))
                .build());
        modelFirst.add(TimelineEvent.<Task>builder()
                .data(new Task("Create memo", "images/timeline/memo.png", false))
                .startDate(LocalDate.of(2015, 8, 28))
                .build());
        modelFirst.add(TimelineEvent.<Task>builder()
                .data(new Task("Create report", "images/timeline/report.png", true))
                .startDate(LocalDate.of(2015, 8, 31))
                .endDate(LocalDate.of(2015, 9, 3))
                .build());

        // duplicate events with 6 month-shift to check for potential daylight-saving-issues
        new ArrayList<TimelineEvent<Task>>(modelFirst.getEvents()).forEach(e -> {
            modelFirst.add(TimelineEvent.<Task>builder()
                    .data(e.getData())
                    .startDate(e.getStartDate().minusMonths(6))
                    .endDate(e.getEndDate() == null ? null : e.getEndDate().minusMonths(6))
                    .build());
        });
    }

    private void createSecondTimeline() {
        modelSecond = new TimelineModel<>();

        modelSecond.add(TimelineEvent.<String>builder()
                .data("Project A")
                .startDate(LocalDate.of(2015, 8, 23))
                .endDate(LocalDate.of(2015, 8, 30))
                .build());
        modelSecond.add(TimelineEvent.<String>builder()
                .data("Project B")
                .startDate(LocalDate.of(2015, 8, 27))
                .endDate(LocalDate.of(2015, 8, 31))
                .build());
    }

    public void onSelect(TimelineSelectEvent<?> e) {
        // get a thread-safe TimelineUpdater instance for the second timeline
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":form:timelineSecond");

        if (aSelected) {
            // select project B visually (index in the event's list is 1)
            timelineUpdater.select("projectB");
        } else {
            // select project A visually (index in the event's list is 0)
            timelineUpdater.select("projectA");
        }

        aSelected = !aSelected;

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Selected project: " + (aSelected ? "A" : "B"), null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public TimelineModel<Task, ?> getModelFirst() {
        return modelFirst;
    }

    public TimelineModel<String, ?> getModelSecond() {
        return modelSecond;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @RegisterForReflection
    public class Task implements Serializable {

        private final String title;
        private final String imagePath;
        private final boolean period;

        public Task(String title, String imagePath, boolean period) {
            this.title = title;
            this.imagePath = imagePath;
            this.period = period;
        }

        public String getTitle() {
            return title;
        }

        public String getImagePath() {
            return imagePath;
        }

        public boolean isPeriod() {
            return period;
        }
    }

}