package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

import java.util.List;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<ListItem> listItems;

    public MyAdapter(Context context, List<ListItem> listItems) {
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
        ListItem item = listItems.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public Button btn;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemName);
            btn = itemView.findViewById(R.id.dltBtn);

//            btn.setOnClickListener(this);
        }
    }
}
