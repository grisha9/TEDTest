package rzn.ru.myasoedov.tedtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import rzn.ru.myasoedov.tedtest.R;
import rzn.ru.myasoedov.tedtest.dto.Talk;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;

/**
 * Created by grisha on 03.05.15.
 */
public class TalkAdapter extends ArrayAdapter<TalkInfo> {
    private List<TalkInfo> talks;
    private LayoutInflater inflater;
    private Context context;

    public TalkAdapter(Context context, List<TalkInfo> talks) {
        super(context, 0, talks);
        this.talks = talks;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Talk talk = talks.get(position).getTalk();
        holder.text.setText(talk.getName());
        if (talk.getUrls() != null && !talk.getUrls().isEmpty()) {
            Picasso.with(context)
                    .load(talk.getUrls().get(talk.getUrls().size() - 1).getUrl())
                    .into(holder.imageView);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView text;
    }

    public void addTalks(Collection<TalkInfo> collection) {
        talks.addAll(collection);
        notifyDataSetChanged();
    }
}