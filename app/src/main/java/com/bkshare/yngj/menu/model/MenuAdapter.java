package com.bkshare.yngj.menu.model;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkshare.yngj.R;
import com.bkshare.yngj.menu.presenter.inf.OnItemClickListener;

import java.util.List;



public class MenuAdapter extends RecyclerView.Adapter<MenuListViewHolder> implements View.OnClickListener {
    private List<IndexMenu> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MenuAdapter(Context _context, List<IndexMenu> _list) {
        context = _context;
        list = _list;
    }

    @Override
    public MenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.list_menu_main_index_grid, parent,
                false);
        MenuListViewHolder holder = new MenuListViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuListViewHolder deviceListViewTemp, int position) {
        deviceListViewTemp.itemView.setTag(position);
        IndexMenu indexMenu = list.get(position);
        deviceListViewTemp.menu_text.setText(indexMenu.menuText);
        deviceListViewTemp.menu_img.setImageDrawable(indexMenu.imgUrl);
        if (indexMenu.isRunning == 0) {
            deviceListViewTemp.menu_run.setVisibility(View.INVISIBLE);
        } else if(indexMenu.isRunning == 100){
            deviceListViewTemp.menu_run.setVisibility(View.VISIBLE);
            deviceListViewTemp.menu_run.setText("+");
        }
        else
        {
            deviceListViewTemp.menu_run.setVisibility(View.VISIBLE);
            deviceListViewTemp.menu_run.setText(indexMenu.isRunning+"");
        }
    }
    private void setTipAnimation(View view,int per)
    {

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.OnClickItem(v, (int) v.getTag());
        }
    }

    public void setOnclickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
