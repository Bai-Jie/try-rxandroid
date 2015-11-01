package gq.baijie.tryit.rxandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final List<Class<? extends Activity>> ACTIVITY_LIST;

    static {
        List<? extends Class<? extends Activity>> list = Arrays.asList(SimpleActivity.class);
        ACTIVITY_LIST = Collections.unmodifiableList(list);
    }

    @Bind(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    final ActivityListAdapter adapter = new ActivityListAdapter();
    {
        adapter.bindActivityList(ACTIVITY_LIST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mainRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mainRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        mainRecyclerView.setAdapter(adapter);
    }

    private static class ActivityListAdapter extends RecyclerView.Adapter<ActivityCardViewHolder> {

        @Nullable
        private List<Class<? extends Activity>> activityList;

        public void bindActivityList(@Nullable List<Class<? extends Activity>> activityList) {
            this.activityList = activityList;
        }

        @Override
        public ActivityCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ActivityCardViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_view_activity_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ActivityCardViewHolder holder, int position) {
            assert activityList != null;
            final Class<? extends Activity> clazz = activityList.get(position);
            holder.activityNameView.setText(clazz.getSimpleName());
            holder.activityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(new Intent(context, clazz));
                }
            });
        }

        @Override
        public int getItemCount() {
            return activityList != null ? activityList.size() : 0;
        }
    }

    static class ActivityCardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.activity_card_view)
        CardView activityCardView;
        @Bind(R.id.activity_name_view)
        TextView activityNameView;

        public ActivityCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
