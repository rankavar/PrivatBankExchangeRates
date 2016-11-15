package projects.rankavar.privatbankexchangerates.myAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import projects.rankavar.privatbankexchangerates.R;
import projects.rankavar.privatbankexchangerates.data.DataForShow;


/**Adapter for recycler view
 * Created by furch on 22.10.2016.
 */
public class ExpandebleRates extends RecyclerView.Adapter<ExpandebleRates.ViewHolder> {

    private DataForShow data;
    private Context context;
    //interface-listener for controlling clicks from recyclerviews head elements in main activity
    private HeaderRecyclerButtonsClick headerInterface;
    private String text;

    public ExpandebleRates(final DataForShow d,Activity act){
        this.data = d;
        this.headerInterface = (HeaderRecyclerButtonsClick)act;

    }
    public void setNewData(DataForShow newData,String date){
        data = newData;
        text = date;
    }
    @Override
    public int getItemViewType(int position) {
        int itemViewtype;
        if(position ==0){
            // view type of header element
            itemViewtype = 0;
        }else{
            // view type for other elements
            itemViewtype = 1;
        }
        return itemViewtype;
    }

    @Override
    public ExpandebleRates.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        switch (viewType){
            case 0:{
                ExpandebleRates.ViewHolder vh =  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.header_recycler,parent,false),viewType);
                return vh;
            }
            case 1:{
                ExpandebleRates.ViewHolder vh =  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_content,parent,false),viewType);
                return vh;
            }

        }

        return null;

    }

    @Override
    public void onBindViewHolder(final ExpandebleRates.ViewHolder holder, final int position) {
        int adapterViewType = getItemViewType(holder.getAdapterPosition());
        switch (adapterViewType){
            case 0:{
                holder.getTodayRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        headerInterface.getTodayRateButton();
                    }
                });
                holder.getArchiveRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        headerInterface.getArchiveRateButton();
                    }
                });
                if(text!=null){
                    holder.chosenTextView.setVisibility(View.VISIBLE);
                    holder.chosenTextView.setText(text);
                }
                break;
            }
            case 1:{
                int dataPostion = position-1;
                holder.setIsRecyclable(false);
                holder.headerContent.setText(data.getCurrency().get(dataPostion));
                holder.textViewBuy.setText(""+data.getBuy().get(dataPostion));
                holder.textViewSale.setText(""+data.getSale().get(dataPostion));
                holder.expandableLinearLayout.setInRecyclerView(true);
                if(position%2==1) {
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorDarkStandartBackground));
                }
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        onClickButton(holder,position);
                    }
                });
                // change title layout in expandable items
                if(position == 1){
                    holder.expandableLinearLayout.setExpanded(true);
                    holder.rotation = !holder.rotation;
                    holder.imgview.setRotation(holder.rotation ? 180f : 0f);
                    ViewGroup.LayoutParams params = holder.relativeLayout.getLayoutParams();
                    params.height = (int)context.getResources().getDimension(R.dimen.expandeble_size_header_opened);
                    holder.relativeLayout.setLayoutParams(params);
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorStandartBackground));

                }
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(data!=null){
            size = size+data.getCurrency().size();
        }
        return size+1;
    }

    // open expandable contents
    private void onClickButton(final ExpandebleRates.ViewHolder holder,int position ){
        holder.rotation = !holder.rotation;
        holder.imgview.setRotation(holder.rotation ? 180f : 0f);
        holder.expandableLinearLayout.toggle();
        if(position%2==1){
            if(holder.rotation){
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorStandartBackground));

            }else{
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorDarkStandartBackground));
            }

        }
        ViewGroup.LayoutParams params = holder.relativeLayout.getLayoutParams();
        if(!holder.rotation){
            params.height = (int)context.getResources().getDimension(R.dimen.expandeble_size_header_closed);
            holder.relativeLayout.setLayoutParams(params);
        }else {
            params.height = (int)context.getResources().getDimension(R.dimen.expandeble_size_header_opened);
            holder.relativeLayout.setLayoutParams(params);
        }

    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewBuy ;
        TextView textViewSale;
        TextView headerContent;
        RelativeLayout relativeLayout;
        ExpandableLinearLayout expandableLinearLayout;
        ImageView imgview;
        Button getTodayRate;
        Button getArchiveRate;
        TextView chosenTextView;
        public boolean rotation = false;


        public ViewHolder(View v,int viewType){
            super(v);
            switch (viewType){
                case 0:{
                    getTodayRate = (Button)v.findViewById(R.id.getTodayRate);
                    getArchiveRate = (Button)v.findViewById(R.id.getDateButton);
                    chosenTextView = (TextView)v.findViewById(R.id.chosen_date);
                    break;
                }
                case 1:{
                    textViewBuy = (TextView)v.findViewById(R.id.text_content_buy);
                    textViewSale = (TextView)v.findViewById(R.id.text_content_sale);
                    headerContent = (TextView)v.findViewById(R.id.header_section_title);
                    relativeLayout = (RelativeLayout)v.findViewById(R.id.relateivelayoutheader);
                    expandableLinearLayout = (ExpandableLinearLayout)v.findViewById(R.id.expandableLayout);
                    imgview = (ImageView)v.findViewById(R.id.imgArrow);
                }
            }
        }

    }
}
