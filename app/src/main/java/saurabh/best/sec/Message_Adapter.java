package saurabh.best.sec;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.ViewHolder> {
    private Context mContext;
    private List<Messages> msgList;
    private String currentUserId;
    private static final Integer MESSAGE_TYPE_LEFT = 0;
    private static final Integer MESSAGE_TYPE_RIGHT = 1;

    @Override
    public int getItemCount() {
        return this.msgList.size();
    }

    public Message_Adapter(Context mContext, List<Messages> msgList, String currentUserId) {
        this.mContext = mContext;
        this.msgList = msgList;
        this.currentUserId = currentUserId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.mt);
//            text.setText("Hello World");
        }

        public TextView getMessageTextView() {
            return text;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == MESSAGE_TYPE_RIGHT) {
            v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_right, parent, false);
        } else {
            v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_left, parent, false);

        }
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public int getItemViewType(int position) {
        Log.d("", msgList.get(position).getName());
        if (msgList.get(position).getSenderid().equals(this.currentUserId)) {
            return MESSAGE_TYPE_RIGHT;
        } else {
            return MESSAGE_TYPE_LEFT;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("", "Bind View Holder");
        holder.getMessageTextView().setText(msgList.get(position).getText());

    }
}
