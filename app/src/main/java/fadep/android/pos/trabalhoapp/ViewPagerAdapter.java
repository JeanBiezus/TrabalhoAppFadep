package fadep.android.pos.trabalhoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by jean on 28/03/18.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Bitmap> images = new ArrayList<>();

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_view_layout, null);
        ImageView imageView = view.findViewById(R.id.img_produto);

        imageView.setImageBitmap(images.get(position));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        ViewPager viewPager = (ViewPager) container;
        viewPager.removeView(view);
        images.remove(position);
        notifyDataSetChanged();

        if (images.size() > 0)
            viewPager.setCurrentItem(images.size() - 1, true);
    }

    public void addImage(Bitmap image) {
        images.add(image);
        notifyDataSetChanged();
    }

    public List<Bitmap> getImages() {
        return images;
    }
}