package com.yupeng.myappli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends BaseExpandableListAdapter {
    List<CartBean.ResultBean> result;


    public CartAdapter(List<CartBean.ResultBean> list) {
        this.result = list;
    }

    public void setResult(List<CartBean.ResultBean> list) {
        result.clear();
        result.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return result.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return result.get(groupPosition).shoppingCartList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
//父
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
            groupViewHolder =  new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }else
        {
            groupViewHolder = (GroupViewHolder) convertView.getTag();

        }
        CartBean.ResultBean resultBean = result.get(groupPosition);
        String categoryName = resultBean.categoryName;
        groupViewHolder.mTextGroup.setText(categoryName);
        final List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.shoppingCartList;
        boolean groupischecked = true;
        for (int i = 0; i < shoppingCartList.size(); i++) {
            CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
            if (!shoppingCartListBean.isChecked){
                groupischecked = false;
                break;
            }
        }
        groupViewHolder.mCheckGroup.setChecked(groupischecked);
        groupViewHolder.mCheckGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean oldGroup = true;
                for (int i = 0; i < shoppingCartList.size(); i++) {
                    CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
                    if (!shoppingCartListBean.isChecked) {
                        oldGroup = false;
                        break;
                    }
                }
                boolean newGroup =!oldGroup;
                for (int i = 0; i < shoppingCartList.size(); i++) {
                    CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
                    shoppingCartListBean.isChecked = newGroup;
                }
                notifyDataSetChanged();
                if (checkedIS!=null){
                    checkedIS.onGroupChange(groupPosition);
                }
            }
        });
        return convertView;
    }
//子
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = result.get(groupPosition).shoppingCartList.get(childPosition);
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
            childViewHolder=new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.textChild.setChecked(shoppingCartListBean.isChecked);
        childViewHolder.textTitle.setText(shoppingCartListBean.commodityName);
        childViewHolder.textPrice.setText("价格 $:"+shoppingCartListBean.price);
        Glide.with(parent.getContext())
                .load(shoppingCartListBean.pic)
                .into(childViewHolder.imagChild);
        childViewHolder.textChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartListBean.isChecked=!shoppingCartListBean.isChecked;
                notifyDataSetChanged();
                if (checkedIS!=null) {
                    checkedIS.onisCheckChange(groupPosition,childPosition);
                }
            }
        });
//        加减器
        childViewHolder.myView.num=shoppingCartListBean.count;
        childViewHolder.myView.setIvIewChange(new MyViewBuj.IvIewChange() {
            @Override
            public void onCurress(int num) {
                shoppingCartListBean.count=num;
                notifyDataSetChanged();
                if (checkedIS!=null) {
                    checkedIS.onCheckNum(groupPosition,childPosition);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean isAllcheck(){
        boolean isAlled=true;
        for (int i = 0; i <result.size(); i++) {
            CartBean.ResultBean resultBean = result.get(i);
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.shoppingCartList;
            for (int j = 0; j < shoppingCartList.size(); j++) {
                CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(j);
                if (!shoppingCartListBean.isChecked) {
                    isAlled=false;
                }
            }
        }
        return isAlled;
    }
    public double isAllcheckPrice(){
        double checkPrice=0.0;
        for (int i = 0; i <result.size(); i++) {
            CartBean.ResultBean resultBean = result.get(i);
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.shoppingCartList;
            for (int j = 0; j < shoppingCartList.size(); j++) {
                CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(j);
                if (shoppingCartListBean.isChecked) {
                    checkPrice+=shoppingCartListBean.price*shoppingCartListBean.count;
                }
            }
        }
        return checkPrice;
    }

    public int isAllchecknum(){
        int checknum = 0;
        for (int i = 0; i < result.size(); i++) {
            CartBean.ResultBean resultBean = result.get(i);
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.shoppingCartList;
            for (int j = 0; j < shoppingCartList.size(); j++) {
                CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(j);
                if (shoppingCartListBean.isChecked){
                    checknum+=shoppingCartListBean.count;
                }
            }
        }
        return checknum;
    }
    public void changeischeck(boolean ischeck){
        for (int i = 0; i < result.size(); i++) {
            CartBean.ResultBean resultBean = result.get(i);
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.shoppingCartList;
            for (int j = 0; j < shoppingCartList.size(); j++) {
                CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(j);
                shoppingCartListBean.isChecked = ischeck;
            }
            notifyDataSetChanged();
        }
    }
    static class GroupViewHolder {
        @BindView(R.id.check_group)
        CheckBox mCheckGroup;
        @BindView(R.id.text_group)
        TextView mTextGroup;
        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class ChildViewHolder {
        @BindView(R.id.text_child)
        CheckBox textChild;
        @BindView(R.id.imag_child)
        ImageView imagChild;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_price)
        TextView textPrice;
        @BindView(R.id.my_view)
        MyViewBuj myView;
        ChildViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public CheckedIS checkedIS;

    public void setCheckedIS(CheckedIS checkedIS) {
        this.checkedIS = checkedIS;
    }

    public interface CheckedIS{
        void onisCheckChange(int groupPosition,int childPosition);
        void onCheckNum(int groupPosition,int childPosition);
        void onGroupChange(int groupPosition);
    }
}
