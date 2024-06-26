package com.example.had_store.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.had_store.Adapter.SanPhamAdapter;
import com.example.had_store.DAO.SanPhamDao;
import com.example.had_store.Model.SanPham;
import com.example.had_store.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragment_TrangChu extends Fragment {

    private RecyclerView rcvsanphamsale;
    private RecyclerView rcvtopsanpham;
    private SanPhamAdapter adapterSale;
    private SanPhamAdapter adapterTop;
    private SanPhamDao sanPhamDao;
    private Handler handlerSale;
    private Handler handlerTop;
    private int currentSalePosition = 0;
    private int currentTopPosition = 0;
    private ImageView imganhtrangchu;
    private int currentImageIndex = 0;
    private int[] imageResources = {R.drawable.img_3, R.drawable.img_4, R.drawable.img_5, R.drawable.img_6, R.drawable.img_7};
    private Handler handlerImage;
    private String userType;
    private String maKh;
// Declare userType variable



    public Fragment_TrangChu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__trang_chu, container, false);

        rcvsanphamsale = view.findViewById(R.id.rcvsanphamsale);
        rcvtopsanpham = view.findViewById(R.id.rcvtopsanpham);

        LinearLayoutManager layoutManagerHorizontal1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerHorizontal2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rcvsanphamsale.setLayoutManager(layoutManagerHorizontal1);
        rcvtopsanpham.setLayoutManager(layoutManagerHorizontal2);
        sanPhamDao = new SanPhamDao(getContext());

        // List of sale products
        List<SanPham> saleSanPhamList = sanPhamDao.getSaleSanPhamList();
        ArrayList<SanPham> arrayListSaleSanPham = new ArrayList<>(saleSanPhamList);

        // List of top products
        List<SanPham> topSanPhamList = getTop5ProductsInCart();
        ArrayList<SanPham> arrayListTopSanPham = new ArrayList<>(topSanPhamList);
        Bundle args = getArguments();
        userType = args.getString("USER_TYPE");


        if (getContext() != null) {
            adapterSale = new SanPhamAdapter(getContext(), arrayListSaleSanPham, userType,maKh);
            rcvsanphamsale.setAdapter(adapterSale);

            adapterTop = new SanPhamAdapter(getContext(), arrayListTopSanPham,userType,maKh);
            rcvtopsanpham.setAdapter(adapterTop);

            // Set up automatic scrolling for sale products after 2 seconds
            handlerSale = new Handler(Looper.myLooper());
            handlerSale.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (currentSalePosition < adapterSale.getItemCount() - 1) {
                        currentSalePosition++;
                    } else {
                        currentSalePosition = 0;
                    }
                    rcvsanphamsale.smoothScrollToPosition(currentSalePosition);
                    handlerSale.postDelayed(this, 2500); // 2000 milliseconds (2 seconds)
                }
            }, 2500); // Initial delay

            // Set up automatic scrolling for top products after 2 seconds
            handlerTop = new Handler(Looper.myLooper());
            handlerTop.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (currentTopPosition < adapterTop.getItemCount() - 1) {
                        currentTopPosition++;
                    } else {
                        currentTopPosition = 0;
                    }
                    rcvtopsanpham.smoothScrollToPosition(currentTopPosition);
                    handlerTop.postDelayed(this, 2000); // 2000 milliseconds (2 seconds)
                }
            }, 2000); // Initial delay
        }

        imganhtrangchu = view.findViewById(R.id.imganhtrangchu);
        handlerImage = new Handler(Looper.myLooper());
        handlerImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Change the image after 2 seconds
                if (currentImageIndex < imageResources.length - 1) {
                    currentImageIndex++;
                } else {
                    currentImageIndex = 0;
                }
                imganhtrangchu.setImageResource(imageResources[currentImageIndex]);

                // Repeat the process after 2 seconds
                handlerImage.postDelayed(this, 2300); // 2000 milliseconds (2 seconds)
            }
        }, 2300); // Initial delay
        return view;


    }

    @Override
    public void onDestroyView() {
        // Remove the callbacks when the fragment is destroyed to prevent memory leaks
        if (handlerSale != null) {
            handlerSale.removeCallbacksAndMessages(null);
        }
        if (handlerTop != null) {
            handlerTop.removeCallbacksAndMessages(null);
        }
        if (handlerImage != null) {
            handlerImage.removeCallbacksAndMessages(null);
        }
        super.onDestroyView();
    }

    // Method to get the top 5 products with the highest quantity in the shopping cart
    private List<SanPham> getTop5ProductsInCart() {
        // Assuming you have a method in SanPhamDao to retrieve the top 5 products with the highest quantity
        // Replace the method name with the actual method you use to get this information
        return sanPhamDao.getTop5ProductsInCart();
    }
}
