package classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial05.Display;
import com.example.tutorial05.R;
import com.example.tutorial05.Welcome;

import java.util.ArrayList;


public class OfflineDataAdapter extends RecyclerView.Adapter<OfflineDataAdapter.MyViewHolder> {
    private Context context;

    ArrayList<String> userList;
    public OfflineDataAdapter(Context context, ArrayList<String> list) {
        this.context=context;
        this.userList = list;
    }

    @NonNull
    @Override
    public OfflineDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new OfflineDataAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OfflineDataAdapter.MyViewHolder holder, final int position) {
        holder.email.setText(String.valueOf(userList.get(position)));
        holder.listLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clickEmail = new Intent(context, Display.class);
                clickEmail.putExtra("username",String.valueOf(userList.get(position)));
                clickEmail.putExtra("temp",2);
                context.startActivity(clickEmail);
                ((AppCompatActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView email,name;
        LinearLayout inner_linearlayout,listLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.ListEmail);
            email.setVisibility(View.VISIBLE);
            name = itemView.findViewById(R.id.listTxtName);
            name.setVisibility(View.GONE);
            inner_linearlayout = itemView.findViewById(R.id.inner_linearlayout);

            inner_linearlayout.setVisibility(View.GONE);
            listLayout = itemView.findViewById(R.id.listlinearlayout);
        }
    }
}
