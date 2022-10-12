
package com.nide.pocketpass.services

import android.app.assist.AssistStructure
import android.app.assist.AssistStructure.ViewNode
import android.util.Log



internal class StructureParser(private val autofillStructure: AssistStructure) {

    private val TAG = "StructureParser"


    val autofillFields = mutableListOf<ViewNode>()


    fun parseForFill() {
        parse(true)
    }

    fun parseForSave() {
        parse(false)
    }

    /**
     * Traverse AssistStructure and add ViewNode metadata to a flat list.
     */
    private fun parse(forFill: Boolean) {
        val nodes = autofillStructure.windowNodeCount
//        filledAutofillFieldCollection = FilledAutofillFieldCollection()
        Log.d(TAG, " node: $nodes; Parsing structure for " + autofillStructure.activityComponent)
        for (i in 0 until nodes) {
            parseLocked(forFill, autofillStructure.getWindowNodeAt(i).rootViewNode)
        }
    }

    private fun parseLocked(forFill: Boolean, viewNode: ViewNode) {

        Log.i(TAG, "hint:${viewNode.hint} \n autoHint = ${viewNode.autofillHints} \n id: ${viewNode.autofillId} \n option:${viewNode.autofillOptions} \n type:${viewNode.autofillType} \n value: ${viewNode.autofillValue}")

        viewNode.hint?.let { hint->
            if(hint.isNotEmpty()){
                autofillFields.add(viewNode)
            }
        }

       /* viewNode.autofillHints?.let { autofillHints ->
            if (autofillHints.isNotEmpty()) {
               /* if (forFill) {
                    autofillFields.add(AutofillFieldMetadata(viewNode))
                } else {
                    filledAutofillFieldCollection.add(FilledAutofillField(viewNode))
                }*/

                Log.i(TAG, "parseLocked: hints= $autofillHints")
            }
        }*/
        val childrenSize = viewNode.childCount
        for (i in 0 until childrenSize) {
            parseLocked(forFill, viewNode.getChildAt(i))
        }
    }
}
