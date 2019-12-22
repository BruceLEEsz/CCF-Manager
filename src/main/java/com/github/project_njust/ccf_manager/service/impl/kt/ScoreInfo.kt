package com.github.project_njust.ccf_manager.service.impl.kt

import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.model.ExamScore
import com.github.project_njust.ccf_manager.service.IResponse
import com.github.project_njust.ccf_manager.service.ISubmitData
import com.github.project_njust.ccf_manager.service.kt.CoroutinesService
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import kotlin.math.sqrt

object ScoreInfo : CoroutinesService("scoreInfo", UserType.ADMIN) {

    val scoresGetter = mapOf<Int, (ExamScore) -> Int>(
            0 to { it ->
                it.examgrade
            },
            1 to { it ->
                it.data.getNumberList(ExamScore.DETAILED_SCORE)?.get(0)?.toInt() ?: 0
            },
            2 to { it ->
                it.data.getNumberList(ExamScore.DETAILED_SCORE)?.get(1)?.toInt() ?: 0
            },
            3 to { it ->
                it.data.getNumberList(ExamScore.DETAILED_SCORE)?.get(2)?.toInt() ?: 0
            },
            4 to { it ->
                it.data.getNumberList(ExamScore.DETAILED_SCORE)?.get(3)?.toInt() ?: 0
            },
            5 to { it ->
                it.data.getNumberList(ExamScore.DETAILED_SCORE)?.get(4)?.toInt() ?: 0
            }
    )

    /*
     * type: 'score'|'interval',
     * index: 0,1,2,3,4,5
     */
    override suspend fun onCoroutinesRequest(input: ISubmitData): IResponse {
        val last = SQLManager.getExamInfoManager().lastInfo
                ?: return IResponse.createIResponse(IResponse.Status.ERROR).let {
                    it["reason"] = "未找到任何考试信息"
                    it
                }

        val type = input.data.getString("type")
                ?: return IResponse.createIResponse(IResponse.Status.ERROR).let {
                    it["reason"] = "参数错误"
                    it
                }
        val index = input.data.getInt("index")
        val score = SQLManager
                .getExamScoreManager()
                .selectAllExamScore(last.examid)
                .map(scoresGetter[index]!!)
                .sorted()
        if (score.isEmpty()) {
            return IResponse.createIResponse(IResponse.Status.ERROR).let {
                it["reason"] = "没有成绩数据"
                it
            }
        }
        when (type) {
            "score" -> {
                return IResponse.createIResponse().let {
                    val average: Double = score.average()
                    val median: Int = score.elementAt(score.size / 2)
                    val variance = score.map {
                        Math.pow(it - average, 2.0)
                    }.sum() / score.size
                    it["average"] = average
                    it["median"] = median
                    it["variance"] = sqrt(variance)
                    it
                }
            }
            "interval" -> {
                return IResponse.createIResponse().let {
                    val list = mutableListOf<JsonSection>()
                    val inte = if (index > 0) 20 else 100
                    for (i in 1..5) {
                        val max = inte * i
                        val min = max - inte
                        val json = JsonSection.createSection()
                        json.set("name", "${
                        if (min != 0) {
                            min + 1
                        } else {
                            min
                        }
                        }-$max")
                        val zone = (min + 1)..max
                        var has = 0
                        for (sc in score) {
                            if (sc == 0 && 1 in zone) {
                                has++
                            } else if (sc in zone) {
                                has++
                            }
                        }
                        json.set("value", has)
                        list += json
                    }
                    it["result"] = list
                    it
                }
            }
            else -> {
                return IResponse.createIResponse(IResponse.Status.ERROR).let {
                    it["reason"] = "参数错误"
                    it
                }
            }
        }

    }
}