package com.madrapps.dagger.toolwindow;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.util.ui.ColumnInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class DaggerPanel extends SimpleToolWindowPanel {

    private JPanel contentPanel;
    private JBScrollPane scrollPane;
    private TreeTable treeTable;
    private final ListTreeTableModel listTreeTableModel = new ListTreeTableModel(new DefaultMutableTreeNode("Sample root node"), ColumnInfo.EMPTY_ARRAY);

    public DaggerPanel(ToolWindow toolWindow) {
        super(true, false);
        final Content content = ContentFactory.SERVICE.getInstance().createContent(this, "", false);
        toolWindow.getContentManager().addContent(content);
        setContent(contentPanel);

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Sample root node");
        for (int i = 0; i < 100; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode("Node #" + (i + 1));
            rootNode.add(node);
        }
        listTreeTableModel.setRoot(rootNode);
        treeTable.setModel(listTreeTableModel);
        listTreeTableModel.reload(rootNode);
    }

    private void createUIComponents() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Sample root node");
        for (int i = 0; i < 4; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode("Node #" + (i + 1));
            rootNode.add(node);
        }
        ListTreeTableModel listTreeTableModel = new ListTreeTableModel(new DefaultMutableTreeNode("Sample root node"), ColumnInfo.EMPTY_ARRAY);
        treeTable = new TreeTable(listTreeTableModel);
    }
}
