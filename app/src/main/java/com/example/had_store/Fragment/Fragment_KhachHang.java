package com.example.had_store.Fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.example.had_store.Adapter.KhachHangAdapter;
import com.example.had_store.DAO.KhachHangDao;
import com.example.had_store.Model.KhachHang;
import com.example.had_store.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class Fragment_KhachHang extends Fragment {
    private ListView lvKhachHang;
    private ArrayList<KhachHang> list;
    private static KhachHangDao dao;
    private KhachHangAdapter adapter;
    private KhachHang selectedKhachHang;
    private FloatingActionButton fabKhachHang;
    private SearchView timkiemKh;
    private Dialog dialog;
    private EditText edMaKh, edTenKh, edSoKh, edEmailKh, eddiachiKh, edAnhKh, edMatKhauKh;
    private Button btnSave, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__khach_hang, container, false);
        lvKhachHang = v.findViewById(R.id.lvKhachHang);
        fabKhachHang = v.findViewById(R.id.fabKhachHang);
        dao = new KhachHangDao(getActivity());
        timkiemKh = v.findViewById(R.id.timkiemKh);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        timkiemKh.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        timkiemKh.setMaxWidth(Integer.MAX_VALUE);

        timkiemKh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                if (newText.isEmpty()) {
                    capNhatListView();
                }
                return true;
            }
        });

        timkiemKh.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                capNhatListView();
                return false;
            }
        });

        capNhatListView();

        fabKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNhanVienDialog(getActivity(), 0, null);
            }
        });

        lvKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedKhachHang = list.get(i);
                openNhanVienDialog(getActivity(), 1, selectedKhachHang);
            }
        });

        return v;
    }

    private void capNhatListView() {
        List<KhachHang> khachHangList = dao.getAllEmployees();
        list = new ArrayList<>(khachHangList);
        adapter = new KhachHangAdapter(getActivity(), list, dao);
        lvKhachHang.setAdapter(adapter);
    }


    public void openNhanVienDialog(final Context context, final int type, final KhachHang selectedKhachHang) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_khachhang);
        edMaKh = dialog.findViewById(R.id.edMaKh);
        edTenKh = dialog.findViewById(R.id.edTenKh);
        edSoKh = dialog.findViewById(R.id.edSoKh);
        edEmailKh = dialog.findViewById(R.id.edEmailKh);
        eddiachiKh = dialog.findViewById(R.id.edDiaChiKh);
        edAnhKh = dialog.findViewById(R.id.edAnhKh);
        edMatKhauKh = dialog.findViewById(R.id.edMatKhauKh);
        btnSave = dialog.findViewById(R.id.btnSaveKh);
        btnCancel = dialog.findViewById(R.id.btnCancelKh);
        edMaKh.setEnabled(type == 0);

        if (type == 1 && selectedKhachHang != null) {
            edMaKh.setText(selectedKhachHang.getMaKh());
            edTenKh.setText(selectedKhachHang.getTenKh());
            edSoKh.setText(String.valueOf(selectedKhachHang.getSoKh()));
            edEmailKh.setText(selectedKhachHang.getEmailKh());
            eddiachiKh.setText(selectedKhachHang.getDiaChiKh());
            edAnhKh.setText(selectedKhachHang.getAnhKh());
            edMatKhauKh.setText(selectedKhachHang.getMatKhauKh());
        }
        Button btnCal = dialog.findViewById(R.id.btnCancelKh);
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soKhStr = edSoKh.getText().toString().trim();
                String imageUrl = edAnhKh.getText().toString().trim();
                if (imageUrl.isEmpty()) {
                    // Use a default image from drawable
                    Resources resources = getResources();
                    Drawable defaultDrawable = resources.getDrawable(R.drawable.erro);
                    imageUrl = defaultDrawable.toString();
                }
                if (!soKhStr.isEmpty()) {
                    try {
                        int soKh = Integer.parseInt(soKhStr);

                        KhachHang khachHang = new KhachHang();
                        khachHang.setMaKh(edMaKh.getText().toString());
                        khachHang.setTenKh(edTenKh.getText().toString());
                        khachHang.setSoKh(soKh);
                        khachHang.setEmailKh(edEmailKh.getText().toString());
                        khachHang.setDiaChiKh(eddiachiKh.getText().toString());
                        khachHang.setAnhKh(edAnhKh.getText().toString());
                        khachHang.setMatKhauKh(edMatKhauKh.getText().toString());

                        long result = -1;

                        switch (type) {
                            case 0:
                                result = dao.insert(khachHang);
                                break;
                            case 1:
                                result = dao.update(khachHang);
                                break;
                        }

                        if (result > 0) {
                            Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                            capNhatListView();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Nhập số không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Vui lòng không bỏ trống các ô (Trừ link anh)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}

