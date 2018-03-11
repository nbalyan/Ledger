package btventures.ledger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import btventures.ledger.json.AgentInfo;


public class AgentAdapter extends ArrayAdapter<AgentInfo> {
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;

    //private String mAppend;
    private ArrayList<AgentInfo> agents;


    public AgentAdapter(Context context, int layoutResource, ArrayList<AgentInfo> agents) {
        super(context, layoutResource, agents);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        this.agents = agents;
    }

    private static class ViewHolder{
        TextView mail;
        TextView name;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
        Viewholder build pattern (Similar to recyclerview)
         */
        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            //holder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.gridImageProgressbar);
            //holder.image = (ImageView) convertView.findViewById(R.id.cat_photo);
            holder.mail =  (TextView) convertView.findViewById(R.id.agent_mail);
            holder.name =  (TextView) convertView.findViewById(R.id.agent_name);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mail.setText(agents.get(position).getEmail());
        holder.name.setText(agents.get(position).getAgentName());
        return convertView;
    }
}
