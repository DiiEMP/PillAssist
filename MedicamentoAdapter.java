// MedicamentoAdapter.java
package com.example.pillassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MedicamentoAdapter extends ArrayAdapter<Medicamento> {
    private Context context;
    private int resource;
    private List<Medicamento> medicamentos;

    public MedicamentoAdapter(Context context, int resource, List<Medicamento> medicamentos) {
        super(context, resource, medicamentos);
        this.context = context;
        this.resource = resource;
        this.medicamentos = medicamentos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listItem = inflater.inflate(resource, null);
        }

        Medicamento medicamento = medicamentos.get(position);

        TextView nombreTextView = listItem.findViewById(R.id.nombreMedicamentoTextView);
        TextView dosisDiariaTextView = listItem.findViewById(R.id.dosisDiariaTextView);
        TextView lapsoTextView = listItem.findViewById(R.id.lapsoTextView);

        nombreTextView.setText(medicamento.getNombre());
        dosisDiariaTextView.setText("Cada: " + medicamento.getDosisDiaria());
        lapsoTextView.setText("Lapso: " + medicamento.getLapso());

        return listItem;
    }
}
