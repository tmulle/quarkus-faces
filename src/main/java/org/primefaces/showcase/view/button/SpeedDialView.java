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
package org.primefaces.showcase.view.button;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.primefaces.model.badge.BadgeModel;
import org.primefaces.model.badge.DefaultBadgeModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@Named
@RequestScoped
@RegisterForReflection(serialization = true)
public class SpeedDialView {

    private MenuModel model;

    private BadgeModel badgeModel;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        DefaultMenuItem item = DefaultMenuItem.builder()
                .icon("pi pi-pencil")
                .command("#{speedDialView.add}")
                .update("messages")
                .build();
        model.getElements().add(item);

        item = DefaultMenuItem.builder()
                .icon("pi pi-refresh")
                .command("#{speedDialView.update}")
                .update("messages")
                .build();
        model.getElements().add(item);

        item = DefaultMenuItem.builder()
                .icon("pi pi-trash")
                .command("#{speedDialView.delete}")
                .update("messages")
                .build();
        model.getElements().add(item);

        item = DefaultMenuItem.builder()
                .icon("pi pi-upload")
                .outcome("/ui/file/upload/simple")
                .build();
        model.getElements().add(item);

        item = DefaultMenuItem.builder()
                .icon("pi pi-external-link")
                .url("https://www.primefaces.org")
                .build();
        model.getElements().add(item);

        badgeModel = DefaultBadgeModel.builder()
                .value("1")
                .severity("danger")
                .build();
    }

    public MenuModel getModel() {
        return model;
    }

    public BadgeModel getBadgeModel() {
        return badgeModel;
    }

    public void add() {
        addMessage("Add", "Data Added");
    }

    public void update() {
        addMessage("Update", "Data Updated");
    }

    public void delete() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete", "Data Deleted");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
