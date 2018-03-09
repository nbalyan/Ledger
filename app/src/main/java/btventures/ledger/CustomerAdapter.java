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


public class CustomerAdapter extends ArrayAdapter<Customer> {
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;

    //private String mAppend;
    private ArrayList<Customer> customers;


    public CustomerAdapter(Context context, int layoutResource, ArrayList<Customer> customers) {
        super(context, layoutResource, customers);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        this.customers = customers;
    }

    private static class ViewHolder{
        TextView account;
        TextView name;
        TextView address;
        TextView phone;
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
            holder.account =  (TextView) convertView.findViewById(R.id.account);
            holder.name =  (TextView) convertView.findViewById(R.id.name);
            holder.address =  (TextView) convertView.findViewById(R.id.address);
            holder.phone =  (TextView) convertView.findViewById(R.id.phone);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.account.setText(customers.get(position).getAccount());
        holder.name.setText(customers.get(position).getName());
        holder.address.setText(customers.get(position).getAccount());
        holder.phone.setText(customers.get(position).getPhone());

        /*String imgURL = getItem(position).get("URL");
        holder.text.setText(imgURLs.get(position).get("CATEGORY_TEXT"));
        ImageLoader imageLoader = ImageLoader.getInstance();
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        imageLoader.displayImage(imgURL, holder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });*/

        return convertView;
    }
}
