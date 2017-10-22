package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.banner.ImageLoaderBanner;
import com.bawie.yangqingqing.mytaobao.bean.ShouyeBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class ShouyeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public final static int TYPE_1=0;
    public final static int TYPE_2=1;
    public final static int TYPE_3=2;
    public final static int TYPE_4=3;
    private RecyclerView.ViewHolder myViewHolder1=null;

    private ShouyeBean bean;
    private Context context;

    public ShouyeAdapter(ShouyeBean bean, Context context) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_1;
        }else if (position==1){
            return TYPE_2;
        }else if (position==getItemCount()-1){
            System.out.println("aaaaa----------");
            return TYPE_3;
        }else{
            System.out.println("bbbbbbbbbb----------");
            return TYPE_4;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_1:
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lunbo,parent,false);
                myViewHolder1=new MyLunboViewHolder(view);
                break;
            case TYPE_2:
                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.view1,parent,false);
                myViewHolder1=new MyFirstViewHolder(view1);
                System.out.println("111");

                break;
            case TYPE_3:
                for (int i = 0; i <bean.getData().getSubjects().size(); i++) {
                    View view2=LayoutInflater.from(parent.getContext()).inflate(R.layout.view2,parent,false);
                    myViewHolder1=new MySecondViewHolder(view2);
                }
                break;
            case TYPE_4:
                View view3=LayoutInflater.from(parent.getContext()).inflate(R.layout.view3,parent,false);
                 myViewHolder1=new MyThirdViewHolder(view3);
                break;
        }
        return myViewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyLunboViewHolder){
            ((MyLunboViewHolder) holder).banner.setImageLoader(new ImageLoaderBanner());
            List<String> bannerlist=new ArrayList<>();
            for (int i = 0; i < bean.getData().getAd1().size(); i++) {
                System.out.println("============bean"+bean.getData().getAd1().size());
                bannerlist.add(bean.getData().getAd1().get(i).getImage());
            }
            System.out.println("=============bannerlist"+bannerlist.size());
            MyLunboViewHolder lunboviewHolder= (MyLunboViewHolder) holder;
            ((MyLunboViewHolder) holder).banner.setImages(bannerlist);
            ((MyLunboViewHolder) holder).banner.start();
            ImageLoader.getInstance().displayImage(bean.getData().getAd5().get(0).getImage(),lunboviewHolder.lunboiv1);
            ImageLoader.getInstance().displayImage(bean.getData().getAd5().get(1).getImage(),lunboviewHolder.lunboiv2);
            ImageLoader.getInstance().displayImage(bean.getData().getAd5().get(2).getImage(),lunboviewHolder.lunboiv3);
            ImageLoader.getInstance().displayImage(bean.getData().getAd5().get(3).getImage(),lunboviewHolder.lunboiv4);
            ((MyLunboViewHolder) holder).lunbo_tv1.setText(bean.getData().getAd5().get(0).getTitle());
            ((MyLunboViewHolder) holder).lunbo_tv2.setText(bean.getData().getAd5().get(1).getTitle());
            ((MyLunboViewHolder) holder).lunbo_tv3.setText(bean.getData().getAd5().get(2).getTitle());
            ((MyLunboViewHolder) holder).lunbo_tv4.setText(bean.getData().getAd5().get(3).getTitle());
        } else if (holder instanceof MyFirstViewHolder){
            MyFirstViewHolder first= (MyFirstViewHolder) holder;
            first.view1_title1.setText(bean.getData().getAd8().get(0).getTitle());
            first.view1_title2.setText(bean.getData().getAd8().get(1).getTitle());
            first.view1_title3.setText(bean.getData().getAd8().get(2).getTitle());
            first.view1_dec1.setText(bean.getData().getAd8().get(0).getDescription());
            first.view1_dec2.setText(bean.getData().getAd8().get(1).getDescription());
            first.view1_dec3.setText(bean.getData().getAd8().get(2).getDescription());
            ImageLoader.getInstance().displayImage(bean.getData().getAd8().get(0).getImage(),first.image1);
            ImageLoader.getInstance().displayImage(bean.getData().getAd8().get(1).getImage(),first.image2);
            ImageLoader.getInstance().displayImage(bean.getData().getAd8().get(2).getImage(),first.image3);
        } else if (holder instanceof MySecondViewHolder){
            ((MySecondViewHolder) holder).view2_rv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            SecondAdapter secondAdapter=new SecondAdapter(bean.getData().getDefaultGoodsList(),context);
            ((MySecondViewHolder) holder).view2_rv.setAdapter(secondAdapter);
            secondAdapter.setRecyOnClick(new SecondAdapter.OnChilk(){
                @Override
                public void RecyOnClick(int position) {
                    Toast.makeText(context,"你点击了"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }else if(holder instanceof MyThirdViewHolder){
            System.out.println("---s----s-------sssssssss-");
            if (position-2<6){
                MyThirdViewHolder thirdViewHolder= (MyThirdViewHolder) holder;
                ImageLoader.getInstance().displayImage(bean.getData().getSubjects().get(position-2).getImage(),thirdViewHolder.view3_iv);
                ((MyThirdViewHolder) holder).view3_rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                ((MyThirdViewHolder) holder).view3_rv.setAdapter(new ThirdAdapter(context,bean.getData().getSubjects().get(position-2).getGoodsList()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3+bean.getData().getSubjects().size();
    }
    class MyLunboViewHolder extends RecyclerView.ViewHolder{
        private final Banner banner;
        private final ImageView lunboiv1;
        private final ImageView lunboiv2;
        private final ImageView lunboiv3;
        private final ImageView lunboiv4;
        private final TextView lunbo_tv1;
        private final TextView lunbo_tv2;
        private final TextView lunbo_tv3;
        private final TextView lunbo_tv4;

        public MyLunboViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.lunbo_banner);
            lunboiv1 = (ImageView) itemView.findViewById(R.id.lunbo_iv1);
            lunboiv2 = (ImageView) itemView.findViewById(R.id.lunbo_iv2);
            lunboiv3 = (ImageView) itemView.findViewById(R.id.lunbo_iv3);
            lunboiv4 = (ImageView) itemView.findViewById(R.id.lunbo_iv4);
            lunbo_tv1 = (TextView) itemView.findViewById(R.id.lunbo_tv1);
            lunbo_tv2 = (TextView) itemView.findViewById(R.id.lunbo_tv2);
            lunbo_tv3 = (TextView) itemView.findViewById(R.id.lunbo_tv3);
            lunbo_tv4 = (TextView) itemView.findViewById(R.id.lunbo_tv4);
        }
    }
    class MyFirstViewHolder extends RecyclerView.ViewHolder{

        private final ImageView image1;
        private final ImageView image2;
        private final ImageView image3;
        private final TextView view1_title1;
        private final TextView view1_title2;
        private final TextView view1_title3;
        private final TextView view1_dec1;
        private final TextView view1_dec2;
        private final TextView view1_dec3;

        public MyFirstViewHolder(View itemView) {
            super(itemView);
            image1 = (ImageView) itemView.findViewById(R.id.view1_iv1);
            image2 = (ImageView) itemView.findViewById(R.id.view1_iv2);
            image3 = (ImageView) itemView.findViewById(R.id.view1_iv3);
            view1_title1 = (TextView) itemView.findViewById(R.id.view1_title1);
            view1_title2 = (TextView) itemView.findViewById(R.id.view1_title2);
            view1_title3 = (TextView) itemView.findViewById(R.id.view1_title3);
            view1_dec1 = (TextView) itemView.findViewById(R.id.view1_dec1);
            view1_dec2 = (TextView) itemView.findViewById(R.id.view1_dec2);
            view1_dec3 = (TextView) itemView.findViewById(R.id.view1_dec3);
        }
    }
    class MySecondViewHolder extends RecyclerView.ViewHolder{

        private final RecyclerView view2_rv;

        public MySecondViewHolder(View itemView) {
            super(itemView);
            view2_rv = (RecyclerView) itemView.findViewById(R.id.view2_rv);
        }
    }
    class MyThirdViewHolder extends RecyclerView.ViewHolder{
        private final RecyclerView view3_rv;
        private final ImageView view3_iv;
        public MyThirdViewHolder(View itemView) {
            super(itemView);
            view3_rv = (RecyclerView) itemView.findViewById(R.id.view3_rv);
            view3_iv = (ImageView) itemView.findViewById(R.id.view3_iv);
        }
    }
}
