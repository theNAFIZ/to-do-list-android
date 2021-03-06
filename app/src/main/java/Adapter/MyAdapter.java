package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

import java.util.List;

import Data.DatabaseHandler;
import Model.Todo;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<Todo> listItems;

    public MyAdapter(Context context, List<Todo> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Todo item = listItems.get(position);
        holder.title.setText(item.getTitle());
        if (!item.isCompleted()) {
            holder.status.setText("On progress!");
        } else {
            holder.status.setText("Completed!");

        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView status;
        public Button editBtn;
        public Button deleteBtn;
        public LayoutInflater inflater;
        public AlertDialog.Builder builder;
        public AlertDialog dialog;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemName);
            status = itemView.findViewById(R.id.itemStatus);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.dltBtn);

            editBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.editBtn:
                    edit();
                    break;
                case R.id.dltBtn:
                    delete();
                    break;
                default:
                    break;
            }
        }

        public void edit() {
            int position = getAdapterPosition();
            Todo todo = listItems.get(position);

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.input_form, null);

            EditText editTextInput = view.findViewById(R.id.dInputBox);
            editTextInput.setText(todo.getTitle());

            CheckBox editCompleted = view.findViewById(R.id.completedBox);
            editCompleted.setChecked(todo.isCompleted());

            Button updateButton = view.findViewById(R.id.updateBtn);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            updateButton.setOnClickListener(v -> {
                DatabaseHandler dbHandler = new DatabaseHandler(context);
                if (!editTextInput.getText().toString().isEmpty()) {
                    todo.setTitle(editTextInput.getText().toString());
                    todo.setCompleted(editCompleted.isChecked());
                    dbHandler.updateTodo(todo);
                    Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position, todo);
                    dialog.dismiss();
                }
            });
        }

        public void delete() {
            DatabaseHandler dbHandler = new DatabaseHandler(context);
            int position = getAdapterPosition();
            dbHandler.deleteTodo(listItems.get(position));
            listItems.remove(position);
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
            notifyItemRemoved(position);
        }
    }
}