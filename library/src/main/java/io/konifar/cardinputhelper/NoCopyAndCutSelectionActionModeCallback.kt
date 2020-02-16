package io.konifar.cardinputhelper

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

class NoCopyAndCutSelectionActionModeCallback : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        removeItemIfNeed(menu, android.R.id.copy)
        removeItemIfNeed(menu, android.R.id.cut)
        return true
    }

    private fun removeItemIfNeed(menu: Menu?, id: Int) {
        if (menu?.findItem(id) != null) {
            menu.removeItem(id)
        }
    }

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?) = false

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onDestroyActionMode(mode: ActionMode?) = Unit
}