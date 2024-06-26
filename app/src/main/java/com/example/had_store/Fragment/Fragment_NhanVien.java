package com.example.had_store.Fragment;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.had_store.Adapter.NhanVienAdapter;
import com.example.had_store.DAO.NhanVienDao;
import com.example.had_store.Model.NhanVien;
import com.example.had_store.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Fragment_NhanVien extends Fragment {
    ListView lvNhanVien;
    ArrayList<NhanVien> list;
    static NhanVienDao dao;
    NhanVienAdapter adapter;
    NhanVien selectedNhanVien;
    FloatingActionButton fabNhanVien;
    SearchView timkiem;
    Dialog dialog;
    EditText edMaNv, edTenNv, edSoNv, edEmailNv, edAnhNv, edMatKhauNv;
    Button btnSave, btnCancel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__nhan_vien, container, false);
        lvNhanVien = v.findViewById(R.id.lvNhanVien);
        fabNhanVien = v.findViewById(R.id.fabNhanVien);
        dao = new NhanVienDao(getActivity());
        timkiem = v.findViewById(R.id.timkiemNv);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        timkiem.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        timkiem.setMaxWidth(Integer.MAX_VALUE);

        timkiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list based on the search query
                adapter.getFilter().filter(newText);
                if (newText.isEmpty()) {
                    adapter = new NhanVienAdapter(getActivity(), list, dao);
                    lvNhanVien.setAdapter(adapter);
                }

                return true;
            }
        });
        timkiem.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Reset the adapter with the original data when the search view is closed
                adapter = new NhanVienAdapter(getActivity(), list, dao);
                lvNhanVien.setAdapter(adapter);
                return false;
            }
        });

        // Ensure that capNhatListView is called before setting the adapter
        capNhatListView();

        fabNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNhanVienDialog(getActivity(), 0, null);
            }
        });

        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNhanVien = list.get(i);
                openNhanVienDialog(getActivity(), 1, selectedNhanVien);
            }
        });

        return v;
    }


    void capNhatListView() {
        list = (ArrayList<NhanVien>) dao.getAllEmployees();
        adapter = new NhanVienAdapter(getActivity(), list, dao);
        lvNhanVien.setAdapter(adapter);
    }

    public void openNhanVienDialog(final Context context, final int type, final NhanVien selectedNhanVien) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_nhanvien);
        edMaNv = dialog.findViewById(R.id.edMaNv);
        edTenNv = dialog.findViewById(R.id.edTenNv);
        edSoNv = dialog.findViewById(R.id.edSoNv);
        edEmailNv = dialog.findViewById(R.id.edEmailNv);
        edAnhNv = dialog.findViewById(R.id.edAnhNv);
        edMatKhauNv = dialog.findViewById(R.id.edMatKhauNv);
        btnSave = dialog.findViewById(R.id.btnSaveNv);
        btnCancel = dialog.findViewById(R.id.btnCancelNv);
        edMaNv.setEnabled(type == 0);

        if (type == 1 && selectedNhanVien != null) {
            edMaNv.setText(selectedNhanVien.getMaNv());
            edTenNv.setText(selectedNhanVien.getTenNv());
            edSoNv.setText(String.valueOf(selectedNhanVien.getSoNv()));
            edEmailNv.setText(selectedNhanVien.getEmailNv());
            edAnhNv.setText(selectedNhanVien.getAnhNv());
            edMatKhauNv.setText(selectedNhanVien.getMatKhauNv());
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNv(edMaNv.getText().toString());
                nhanVien.setTenNv(edTenNv.getText().toString());
                nhanVien.setSoNv(Integer.parseInt(edSoNv.getText().toString()));
                nhanVien.setEmailNv(edEmailNv.getText().toString());
                nhanVien.setAnhNv(edAnhNv.getText().toString());
                nhanVien.setMatKhauNv(edMatKhauNv.getText().toString());

                if (type == 0) {
                    long result = dao.insert(nhanVien);
                    if (result > 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    nhanVien.setMaNv(edMaNv.getText().toString());
                    int result = dao.update(nhanVien);
                    if (result > 0) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

                capNhatListView();
                dialog.dismiss();
            }
        });

        dialog.show();
    }




}
