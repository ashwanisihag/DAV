package com.davians.earth.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.davians.earth.R;
import com.davians.earth.activity.UserDetailActivity;
import com.davians.earth.pojo.NearByPOJO;
import com.davians.earth.pojo.SearchPOJO;
import com.davians.earth.util.Pref;
import com.davians.earth.util.StringUtils;
import com.davians.earth.util.UtilFunction;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ashwani Sihag on 03-11-2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<SearchPOJO> items;
    Activity activity;
    Fragment fragment;

    public SearchAdapter(Activity activity, Fragment fragment, List<SearchPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_search_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String schoolName=items.get(position).getSchool();
        if(schoolName.contains("Thermal"))
        {
            Drawable img = ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.common_full_open_on_phone);
            img.setBounds(0, 0, 150, 150);
            holder.search_institute.setCompoundDrawables(null, img, null, null);

        }

        holder.search_institute.setText("School :- " +items.get(position).getSchool());
        holder.search_user_name.setText("Name :- " +items.get(position).getFirstName() + " " + items.get(position).getLastName());

        if (items.get(position).getCity() != null && !TextUtils.isEmpty(items.get(position).getCity())) {
            holder.search_city.setText("City :- " + items.get(position).getCity());
        }
        else {
            holder.search_city.setText("City :- Not Specified");
        }

        if (items.get(position).getPosting() != null && !TextUtils.isEmpty(items.get(position).getPosting())) {
            holder.search_posting.setText("Posted at :- " + items.get(position).getPosting());
        }
        else {
            holder.search_posting.setText("Posted at :- Not Specified");
        }
        if (items.get(position).getJoiningYear() != null && !TextUtils.isEmpty(items.get(position).getJoiningYear())) {
            holder.search_joining_year.setText("Joining Year :- " +UtilFunction.getYear( items.get(position).getJoiningYear()));
        }
        else {
            holder.search_joining_year.setText("Joining Year :- Not Specified");
        }
        holder.search_RollNo.setText("Roll No :- "+ items.get(position).getRollNo().toString());

        if (items.get(position).getDepartment() != null && !TextUtils.isEmpty(items.get(position).getDepartment())) {
            holder.search_department.setText("Department :- " + items.get(position).getDepartment());
        }
        else {
            holder.search_department.setText("Department :- Not Specified");
        }
        if (items.get(position).getProfession() != null && !TextUtils.isEmpty(items.get(position).getProfession())) {
            holder.search_profession.setText("Profession :- " + items.get(position).getProfession());
        }
        else {
            holder.search_profession.setText("Profession :- Not Specified");
        }
        if (items.get(position).getDesignation() != null && !TextUtils.isEmpty(items.get(position).getDesignation())) {
            holder.search_designation.setText("Designation :- " + items.get(position).getDesignation());
        }
        else {
            holder.search_designation.setText("Designation :- Not Specified");
        }
        holder.ll_search_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, UserDetailActivity.class);
                intent.putExtra("userID",items.get(position).getId());
                activity.startActivity(intent);
            }
        });
        if(Integer.parseInt(Pref.GetStringPref(holder.itemView.getContext(), StringUtils.Batch, ""))>Integer.parseInt(UtilFunction.getYear(items.get(position).getLeavingYear())))
        {
            holder.user_rank.setText("Big Brother");
        }
        else
        {
            holder.user_rank.setText("Younger Brother");
        }
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_institute)
        TextView search_institute;
        @BindView(R.id.search_user_name)
        TextView search_user_name;
        @BindView(R.id.search_designation)
        TextView search_designation;
        @BindView(R.id.search_department)
        TextView search_department;
        @BindView(R.id.search_profession)
        TextView search_profession;
        @BindView(R.id.search_RollNo)
        TextView search_RollNo;
        @BindView(R.id.search_city)
        TextView search_city;
        @BindView(R.id.search_posting)
        TextView search_posting;
        @BindView(R.id.search_joining_year)
        TextView search_joining_year;
        @BindView(R.id.ll_back)
        LinearLayout ll_back;
        @BindView(R.id.ll_search_user)
        LinearLayout ll_search_user;
        @BindView(R.id.user_rank)
        TextView user_rank;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
