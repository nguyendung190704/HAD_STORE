package com.example.had_store.Adapter;

// DonHangAdapter.java
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.had_store.DAO.DonHangDao;
import com.example.had_store.Model.DonHang;
import com.example.had_store.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DonHangAdapter extends ArrayAdapter<DonHang> {

    private List<DonHang> originalList;
    private List<DonHang> filteredList;
    private DonHangDao donHangDao;
    private final SimpleDateFormat dateFormat;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        super(context, 0, donHangList);
        this.originalList = new ArrayList<>(donHangList);
        this.filteredList = new ArrayList<>(donHangList);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.donHangDao = new DonHangDao(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_donhang, parent, false);
        }
        ImageView imgDeleteDh = convertView.findViewById(R.id.imgDeleteDh);

        imgDeleteDh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the DonHang object associated with the clicked item
                DonHang donHangToDelete = getItem(position);

                // Call a method to handle the deletion
                handleDeleteDonHang(donHangToDelete);
            }
        });

        DonHang donHang = getItem(position);



        if (donHang != null) {
            TextView tvMaDon = convertView.findViewById(R.id.tvMaDon);
            TextView tvNgayLap = convertView.findViewById(R.id.tvNgayLap);
            TextView tvTrangThaiDon = convertView.findViewById(R.id.tvTrangThaiDon);
            TextView tvMaKh = convertView.findViewById(R.id.tvMaKh);
            TextView tvMaGio = convertView.findViewById(R.id.tvMaGio);

            tvMaDon.setText(String.valueOf("Mã đơn : "+donHang.getMaDon()))  ;
            tvNgayLap.setText(dateFormat.format(donHang.getNgayLap()));
            tvTrangThaiDon.setText("Trạng thái : "+donHang.getTrangThaiDon());
            tvMaGio.setText("Mã giỏ hàng : "+donHang.getMaGio());
            tvMaKh.setText(String.valueOf("Mã khách hàng : "+donHang.getMaKh()));

            TextView tvTongTien = convertView.findViewById(R.id.tvTongTien);

            // Calculate and set the total cost
            int totalCost = donHangDao.calculateTotalCost(donHang.getMaGio());
            tvTongTien.setText("Tổng tiền : " + totalCost);
        }
        return convertView;
    }
    private void handleDeleteDonHang(final DonHang donHangToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("DELETE!");
        builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này không ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call the delete method from DonHangDao to delete the selected DonHang
                deleteDonHang(donHangToDelete);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteDonHang(DonHang donHang) {
        // Create DonHangDao instance
        DonHangDao donHangDao = new DonHangDao(getContext());

        // Delete the DonHang from the database
        int result = donHangDao.delete(donHang.getMaDon());

        if (result > 0) {
            // If deletion is successful, remove the item from the adapter and update the view
            remove(donHang);
            notifyDataSetChanged();
            Toast.makeText(getContext(), "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<DonHang> filteredDonHangs = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // No filter applied, return the original list
                    filteredDonHangs.addAll(originalList);
                } else {
                    // Filter based on maDon
                    int filterValue = Integer.parseInt(constraint.toString());
                    for (DonHang donHang : originalList) {
                        if (donHang.getMaDon() == filterValue) {
                            filteredDonHangs.add(donHang);
                        }
                    }
                }

                results.values = filteredDonHangs;
                results.count = filteredDonHangs.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Clear the filteredList before adding the new filtered data
                filteredList.clear();
                filteredList.addAll((List<DonHang>) results.values);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();
            }
        };
    }
}
