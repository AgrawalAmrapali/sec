package saurabh.best.sec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GrpListAdapter extends ArrayAdapter<Group> {
    private Context mcontext;
    private int mResource;

    public GrpListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Group> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String type = getItem(position).getType();
        String id = getItem(position).getId();
        Group group = new Group(name, type, id);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView t1 = convertView.findViewById(R.id.t1);
        TextView t2 = convertView.findViewById(R.id.t2);
        t1.setText(name);
        t2.setText(type);
        return convertView;
    }
}

