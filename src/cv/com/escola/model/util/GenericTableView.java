package cv.com.escola.model.util;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

public class GenericTableView<T> {
    
    public TableViewSelectionModel<T> tableViewSelectionModel(TableView<T> tableView) {
        final TableViewSelectionModel<T> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        return selectionModel;
    }
    
    public T selected(TableViewSelectionModel<T> selectionModel) {
        T selectedItem = selectionModel.getSelectedItem();
        selectedItem.getClass();
        return selectedItem;
    }
}
