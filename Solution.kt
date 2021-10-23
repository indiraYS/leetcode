class Solution {
    private var findings: Boolean = false

    fun isMatch(s: String, p: String): Boolean {
        if (p.isEmpty() || p.length > 30) return false
        if (s.isEmpty() || s.length > 20) return false

        if (p.indexOf("*") == -1 && p.indexOf(".") == -1 && s != p) {
            return false
        }
        if (p == ".*") return true

        findings = false
        innerMatch(s.split("").filter { x -> x != "" }, p.split("").filter { x -> x != "" }, 0)
        return findings
    }

    private fun innerMatch(s: List<String>, p: List<String>, pStartIndex: Int) {
        for (i in pStartIndex until p.size) {
            when (p.get(index = i)) {
                "*" -> {
                    if (((i - 1) <= s.lastIndex && p.get(index = i - 1) == s.get(index = i - 1)) ||
                        p.get(index = i - 1) == "."
                    ) {
                        run ext@{
                            (0..(s.size - i + 2)).forEach {
                                p.toList().mapIndexed { _idx, _it ->
                                    if (it == 0) {
                                        if (_idx == (i - 1) || _idx == i) {
                                            ""
                                        } else _it
                                    } else if (it == 1) {
                                        if (_idx == i) {
                                            ""
                                        } else _it
                                    } else {
                                        if (_idx == i) {
                                            p.get(index = i - 1)
                                        } else _it
                                    }
                                }.filter { x -> x != "" }.also { xr ->
                                    if (it > 2) {
                                        xr.toMutableList().let { xrr ->
                                            repeat(it - 3) {
                                                xrr.add(i, p.get(index = i - 1))
                                            }

                                            innerMatch(s, xrr, i)
                                            if (this.findings) return@ext
                                        }
                                    } else {
                                        innerMatch(s, xr, i + (if (it > 1) 1 else 0) - 1)
                                        if (this.findings) return@ext
                                    }
                                }

                            }

                        }

                    } else {
                        val arrNew = p.mapIndexed { _idx, _it ->
                            if (_idx == (i - 1) || _idx == i) {
                                ""
                            } else _it


                        }.filter { x -> x != "" }
                        innerMatch(s, arrNew, (i - 1))
                    }
                    return
                }
                else -> {
                    p.get(index = i).let {
                        if (i > s.lastIndex) {
                            if (i + 1 > p.lastIndex) return
                            if (p.get(index = i + 1) != "*") return
                        } else {
                            if (!(it == "." || it == s.get(index = i))) {

                                if (i + 1 > p.lastIndex) return

                                if (p.get(index = i + 1) != "*") {
                                    return
                                }
                            }
                        }
                    }
                }
            }
        }

        if (matchFinal(s, p)) {
            findings = true
        }
    }

    private fun matchFinal(s: List<String>, p: List<String>): Boolean {
        return if (s.size != p.size) false else {
            var index = 0
            while (index < s.size) {
                if (s[index] != p[index] && p[index] != ".") return false
                index++
            }
            true
        }
    }
}
