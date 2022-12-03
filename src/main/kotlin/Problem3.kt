class Problem3 : DailyProblem {

    @JvmInline
    value class Item(val name: Char) {
        val priority
            get() = when (name) {
                in 'a'..'z' -> 1 + name.minus('a')
                in 'A'..'Z' -> 27 + name.minus('A')
                else -> throw Exception("Expecting char in set [a-zA-Z]")
            }
    }

    class Knapsack(contents: String) {
        val allContents: Set<Item>
        val compartment1: Set<Item>
        val compartment2: Set<Item>

        init {
            allContents = contents.toSet().map { Item(it) }.toSet()
            val halfSize = contents.length / 2
            compartment1 = contents.take(halfSize).toSet().map { Item(it) }.toSet()
            compartment2 = contents.takeLast(halfSize).toSet().map { Item(it) }.toSet()
        }

        fun findCommonItem(): Item {
            val common = compartment1 intersect compartment2
            if (common.size != 1) {
                throw Exception("expecting only one item in common")
            }
            return common.first()
        }
    }

    class KnapsackGroup(val kn1: Knapsack, val kn2: Knapsack, val kn3: Knapsack) {
        fun findCommonItem(): Item {
            val common = kn1.allContents intersect kn2.allContents intersect kn3.allContents
            if (common.size != 1) {
                throw Exception("expecting only one item in common")
            }
            return common.first()
        }
    }

    fun solvePart0(): Int {
        return data0.sumOf { Knapsack(it).findCommonItem().priority }
    }

    override fun solvePart1(): Int {
        return data1.sumOf { Knapsack(it).findCommonItem().priority }
    }

    override fun solvePart2(): Int {
        return data1.chunked(3)
            .map { KnapsackGroup(Knapsack(it[0]), Knapsack(it[1]), Knapsack(it[2])) }
            .sumOf { it.findCommonItem().priority }
    }

    companion object {
        val data0 = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.toLines()

        val data1 = """
            dtddvvhwttHJhwdhJPddhwJGppmGjgpQgTjQplQpTljwpg
            BfzSzRSVVMVNRMDDNBSNSnfBmbrglGQbmNpQggFjpgpbQlQb
            ZSBffLnVZdCCPJjhhL
            RGCZpWWWFlHQQbgvFssg
            jLnMzjnrnjjNjhrjdwbHscsVVgDVQPvPwh
            nfJnLMLzjJMtnjNnnBbZtBWBqqbTTTBRpT
            nddlhBtqTBqTVSlBtmCmVcRVmZggfWbcZc
            jDjvPrPSNPwrDNRWbbgWCjRRCcWm
            DzDwSpFrvrvFPQLzQnsqztBthTJnGJqlsJ
            gssGmzwgRgsNmTsqgDnDJnbDHHhhzFdDDh
            WQVFjMWrVQrVvVVjVctSSLSMZhnJZPBnbdnhbnHZZBDJBh
            VCtcccVQLrfvrSlGmfTfNgfmlFgm
            DsmfsBbNNZhDWsbmWmNbbPDHLFjcctjjGcnZGzncnctcGH
            SwVQJrjVwpgSVRpjpVRrlTMCFFCLCFFcHzzGMcHrtHHH
            ppVwTwSwpwvSlSlJTjVVbPhsvvBssWsNfsqWPvWs
            BJwqwJtqqDDDrGDnPFzPFfpphD
            TgZscCHQLSHgZcfMzpjFFjzsshfj
            LcNlTVQCCVLLZTLNvpRtpvBBvRJmNB
            bDBGQBBCTTNPGPPwPzcHfVHrDtLWLVrWVjjHWr
            gpssqqsqlMFfLZQWftjVpr
            lvqqFMRlFcQvbzCNCG
            fhhMDdPhWMJMWvhhSfwRSGlzFbSFNlzw
            LcqTCqcgZqjTggVjcwbFRwbDBTzbRGRwTS
            cHLpZgnCHpQsDdsmQp
            jwStJjJhtgJStpgwJMggQWqQTNTfNTWfbNNMCCNG
            zRZnFPRZPVncPGVFRlRmGHCTqfCCPCHHfLfbTQCbTq
            lnVmFZRZDnRVBFZcrZlhjpggvppthGhphpwprS
            lcttSptHHllQbMcsrltSQGpvNBzpgWBBBDDGWzvgLz
            PdjPVRFhFqFjRRCjzvRWnWLBLgbBBLzg
            hhCCFbPTmjPdhZjhPhZCmTjjMsrJSfHrcmHJrHHmlcJSsmft
            WhWnLZSSnSzQQhfLLNSfmDHrCFDDHtpjGGtTGQCG
            gJbJBcMVwJlRRdbwvwJBVtjdtHHTmptpHTCtTFrFCp
            JwwgvRMJlvJwgqgvqRMcnWWhLPzzsqfnZWnfWWnj
            zdwTSvzHMvVSzDCtZhtGmbTGhm
            lcBHfFjjgtsmDCgshD
            cJPBnqNFnLfHJFPqljclqJzQvSSVWvSnMwvSzSWWdMWM
            pNJMcZrsZDLDcbcccMpQffHqvgdwdFFmdmqwvqmgmzsw
            hhnWjTTStRCGSMgvvgvdqvdFjvVz
            hCTSWhPGttTCGBWMRlTCMSnPBDJpbDfDpNpbbNJfJDJbpJpN
            lbcQcSNFchhQNqHLLqhLqrMpqM
            WfsnsszPWfBBVpHdprrpdnGL
            WTzWfwjtTBzwwBDzmfSSQmmbFZcpQNcbZZbv
            PwSJSlmtPPgwgmHhPPvRvGHLRLQRBQGCQVGb
            rnsFDnnfGGRWQRnW
            dfTTfTFdfrfFFFzQFPJPSSlhqtllNPzgwS
            MMbTFZrcrGZMDqNStWScDtzS
            dvWmhQggQvCnfnqPqDnDjnfP
            lgvdvLClWCQlgdhlrMBBHpGlwbHHGH
            CQHgQpPdCQpsCpzRwSVRSzRZwZ
            JbNBbcbrJvbJnqVznwwTzrzz
            wNbfLvvfDNNBHPFLhddFsQss
            VVzqvwzpqvzqNVVHGNqjHpNfSQDWdWwJdPWrWccdQrWrrDdd
            nLcbtBRtBhcnWSJQlJSgll
            tFbLLLRRhMtsBMtRCRsLCMBVjjvHTNjHHjzcvFFppGHzTT
            QCPrPWNPlWjGGZqGmvdPGd
            JgpHpSfphhfpVmBSgnTvdtddGvZVdvddDv
            LhphBfHpSwSwfHcMgfpmBWWWbsNCjFWsljNbbjlLjb
            QJmQbRmdfmdSQRQZSJltTltNvTrtDtrlftDD
            wpZcHVwwMgBpWMVgWpHLphztDvvGvDPlnGvDLlNrDPnNPl
            McgWFWHHHzVpMgZQFqbjsdjqqRCq
            JPhLFfMJDLQnjNCvWWpdjjdM
            crSwnwVnwSRBcNBNjjWCdC
            GlbTGbsSzrtbmbfhnJQP
            fDLSWVDRHHfVWHgPcZlDlZbbQhBcZQbb
            jrmFmprTpFztmddjdjrpvBQlQZGhQbTsQbGcQbcbQs
            nvqdpmjFnwpLSWlfnVNnWl
            nZBRbBJzznNNCnJZwnBSCJMcpcTpcwhcqhmsmWMwFWLL
            jQfvjgtfvPlHHqWpvWThpWqWch
            VljjjgjQjrTDlDgrHtVCbnJZzNzNbnRNNJZrJR
            MQtJnttlMLlJQsNhQrVVrFVWRRbbVFdJDD
            vGjvzmjzgHqSjjSzmSGHTWbfDFWrbFzFfdDVrfRWDb
            qPRqvTSPggqGgHCmllnCNLtnhcnnsnnw
            zrlZsQMFrsgQFMMjMCbjVDCTCW
            NqHNRdBppcJJcTpdmRfHThpdDWDtvbWVtbLjWbttWqqCCbLt
            mhJpJHTJmBhcJhwhgwzsQwSSlzQQzGlZ
            TvsszlvnzRRVTqzVrqrjjZGPfQPFqPqG
            mcNhDNchppWmWSNhdSmSCQNjPFjrfGjrgPFCrgFPgPgrLf
            SDddWpdMWSwNDmMNwlJRQwJlsVRRvzlsHt
            DTtggjsFFFTlPJhvctBqBqSRmSMBSRnmnRcm
            fGfwZdrbHVLdbGdHHwwQGVwBBCMMfvCNRNSMMMSRBmmRCN
            dGZzGHGVVbvHvHwbzpGbHLrwFDDFTtsglhFspgJshslTDJjT
            CbzspssWwCPcvvplrfqfDCJrDqdllB
            LjttnjNTNGgQQJdBrffTwB
            nVtLSgggjFwtMczhvzpZbSZW
            HCzCHHvWthWFHhssWCVmnqZrnqVrmrmgnbrqmN
            wPPGBjQQGwGbSlSLwgnpnrBZnBBmnMNnMN
            jTTbJlJjPPLPGHHTthhhHcFWTT
            qRdvvPDrCpzPHzcdrrcRqtbJJgjhgtWjJgbWJtgCFb
            GTwGwNscLllGTZmGSTZGlSBMnhggjbgtgbtbsgWFFMhbMF
            ZSQBSmlmzcrdQRqz
            cSpTRphwwghRfgSScqPpnDqDCjDjJJJJDvDLCvvn
            BVmmQFQBQVNBVmsWlbQFGBBlCHTJznzHLHvvCnjjNLHJDLHD
            MFsZZMbBGblbQTmQsFsQMMfPcPcwSpwtStgPphZtctPc
            QZbbZBdjPBjbQQbZnSSltlfwWvlvwNtNjwFMMN
            DLVqTJqpSVtfsptwfWpv
            rcRRVVTSbPQBPrBZ
            tjSgSjLFSnVjDWRsQj
            lcdqhfFpqZGpZqznrVRWPrnWRVBsVG
            FHddNNNHwTHMHvvS
            qCSDSQlwBHNbgJrHnLJH
            GRpRpRfnmRWWVWgVrF
            jhdZjpnvGfTZZQPlCtqQQSsS
            FMZSGWWBrZjMBZMrBWMGjjZDnCRqpgPnbRwPbFnvvqFnDR
            QHcpfVVslfdVlQclcctqRgqgbsCwbCwPCCCPwD
            NLHfLhclmmhdfNNpfQMBmZWBrJMmZWBzMrjj
            pBMpRgBMQwzRthmzLC
            HPcJvrvDbjvrFDcvWrHfHfWHmdddtdTLztmtdtfllmNdNhNz
            DvPFDvnPJLngQsggMGGQ
            BbcFHvbhhDbbTSvZmwwgJPPlDlZldd
            prCrNLMNgWWJBdrJ
            fQMLCfLLtpqsNNMnnfBhcBSVGbhhhcqVbcjc
            ZchcZZjmmNpgmJtgmM
            RLrHllWrQZQGlBpbGFGFFM
            RQnLHrqPLnZHzqjfVPcvVTfCvPTC
            fMtwjfMwrbjfGrtrpPGrwpNNVNVqcbdVqHZTFNbcHSNL
            mgzvDnJmnJhFJHSTNqZLHncHLS
            vRzhzslJFhRffPPQMjGtGl
            VMMNjWppQVwzNWrZdrrtMCMZCtMT
            ngDScLcvPPgDPDGhGDPGSHVbHTHmZtTSrBHZbZBmBb
            LhlglLghnVlplswJjs
            bGJQZZTQQLJJbQZlTZLjCGQTsDhWFhmshhvjWVFVVrgtDsst
            NScqwHcwwnnzBwqPqqsmVNhgsDDVtsghrFFg
            pcrcwnpcffrcBzfbCRLpRLMMRlRLQl
            hzCzCzpRgCzzzCctNsNWNqsZqZhPqNPb
            TdBwmdrrrDmvwTvqNsSRssPlsWsq
            FDBRRHDMTmBfmrmngnpjGgVptMgLCp
            ZPLLnSPMFGvFZMSvHhDhqHfqvfqbDW
            GgcppCgBcrQBBgplrVddhDqqqfdHgWdfqb
            CcCjQszmGBQjrcCwCmCccPwPTPnMPTnMJSMMRZSPJL
            LcVVcqqSHRLzRnCfNnGzNW
            LZPPdljlCggMjgNM
            PTvwlPtwtlJvZTQvbcHppFLHVVTcFssF
            fpWzvzNgWJBVfBJzWzBVJNzWbZcbHhlbthjlrrPrjZZPHZhJ
            hRDmGCFDwQnStncrjnccHcMP
            GmmsGRmFTsFwSCsRQDsCSqqpfvfgzddWggvqdpfBWzVh
            wjRBFljJGDFwwlGGpBSjGDtwTVtTgHHHsHHsVTVzsHqq
            CPLNPdbWvbMWbcmvPNdLVqtsHqgCqHChZhhsVsHt
            PWcPfPvmvNQbbWdWpJjJBDptGnDFjftn
            mFFmJpDMmmnJFjWDVclsSpcflSsQwSsc
            HrjNNjHNfVwLNSSl
            tdZbhjHZHPbdCTvbbhhrGbbHMFmRMvnRRFmmvJMDmgJDJMnq
            szJZhshbsfZJjbttchPctdTnWnRWVWMMnBdLRpMnBz
            SrNwvDSwrCmnVRvjpWLBBn
            ggGmgNFrgSDwmNgrCmtPsZPsjQGsqPcsqqJP
            gjSWSjJSWrWzppzW
            MCMzHNGNqHfscsFtrtwscVcr
            qGHNGNHLCnLmTCHfMMmNTzzldzgJlJZZgJljgTdD
            QGTQtQzTmdTsGTLcdFTGzdtBBjtwvBBJDvDMHJgjJvww
            lPlqsZWnDJjZvZgV
            ShCfCRnWGFsRRRrF
            lwGtndCrrmGCwdmhzQrBzrHvLVggPgHv
            fjMjDZJqSDJfJqDNDjJffjZLHPHHFvVFzHBLgLFpFpBSgL
            MsTZWRNZfJZZqMGVGhhlhhccRnhC
            MMvncqvcHcSnsdzzgvdfQjpljpQVTdDQDRTRlVpQ
            wLCrNtBFFHHThRlH
            bPJtHmCWssqgGPvq
            LvTLsmDWvTWqTsmqjRTmjwgdwgnMHMMFgdtHmBmFVn
            rlSCJzCSfpGGlhznQdnwFhtHgBFwtV
            SGZJJSSrVfCbGJLjPsWbvjRsPTqR
            pNqVVDCMVMBpqJVdMNHrccGHrtNtTFFFrQ
            hwmllWbvvbnPvbSvtrFhhJzzHztcTztT
            WSnbnPbbbvlWlRvnsqqMgLRMjLgVLCJdRV
            GphVTGVMtQwtJmtCJP
            FRRsBBsFqRNZNNrgqBdRfCZvbmPgmQzJQPnmJbJmQPJPPmwj
            RNqsFrRfZZsZWvNqWRFvrBZvWhhCGVplhlWTlTpSCLpMhWMD
            RZRjgbZHjjhsSnRsZstDRStsTVpFhBqFphMqPPpTFQVMPFTM
            zrcGJwNNdwJrfNdJWvGdJzdTlTFlqTVPFTVFPPBpqNTbBP
            WwLdLGfrRLStCZbD
            mrmTqJWTvDDppTDb
            DGzBfCzNDzdMwnLlbn
            FVZPFZFFZPgjmWZsDtsq
            TpnFTnFRCgRgldMRnDnRcrcdbdPBHbtPqbVcccrH
            WNWLfQQmfhhSNwmrcbSVqPtbZDZcPb
            LQhwLQvQvNfJhJRDMGFRlCMDMD
            vLFTDmjVvLgnNHPphN
            lMClGCmsRdCnPzCccngCpz
            dlGZwRsRrRwswGsdSbbZSbVDrVBmDWWWFJrTrFvFTmqV
            SGsZRqGLWLLtZRHRRcLHGTlJjzgJpjzTpNTNJNWpTm
            MPMPvFFvFBrPPDPMQMPChjgpNpSNTmmmpNlTDljlTz
            vnhrvMvnhSRqqLqnfn
            mGFrlBmFQNQFljhqqqqbmHMsTPRbWWCsLMWRsb
            wnwtvpwVzDVpvzzwZppnctMLtMPWWCstTsWTsTLffRRW
            vwDJgZnvZJFqgLBFGqgl
            QdGltnWNWqTdqQWvWsMJcrTcFcrgshJRMs
            BzPLCDPzzzzCCLLfCBzfSDmLMrDJMglrcRbbhRsFhMrRJcsM
            fjSzwwHfSzPzfCVBHlpdjGnZqnZptqQWjGvG
            VbJZbgVzvzmhQpQWpQzhDp
            tHPPcGcFBlCctCGtGcBBNlDLMGfMLwWfwwqMLLJwQWwp
            dCHTPTPJdTBFPdrZjgsjrjnmdgms
            JJpBvJQBZVvcFqqnsWdWvjsn
            DCfbDbTtbgfCSHqqNdFMPhPDFnPPDWsPjM
            bTmzTNCTNmfqTgJQcpLrpZLzVlVL
            dtTLntTjzTftnmwnqGGQHNmm
            SWbShCPMBgBRRFSFtRZZmm
            DlJPCJCgPWhttzpvdjcpVl
            WdzsNvWMzNsMHWddWCVffqmSmScLPvLPgLgLPplrrPmL
            BtnzbnBhbwttwtZlmmlgcwSrLgmmpm
            bFhQtbGBTnjBBbjTtFBbVDzddDDfjdDDqNWVjWHj
            ppmtpgLLZLCbMQvQQThdtrvPhV
            BBlHBwHRjHqBzzbHHqjjQdDQTDhPQDvnQlrQDQvr
            HGjFzwHNczbzRFcGzHGFSJSpspsmpssMLLSZCppmfs
            MpGrMMMcTsHMVHcvbwwmmcRSmDmDmv
            zCNptqCBQQLCNLCzbfvSvbSzSDRDSmSv
            CNNqNgNQJNgQtCqLlllZdZhTrThsnHpVVssPTsGP
            jhSGcShDrLcLLFcw
            MVzQvQNZVLHvHPdhLW
            qzhhQlVbgqjmSjJDsgmR
            CFzSPCgcsVVzFgzSCsBJwjdwJtNllnwglJlp
            QrvbqWvvLbmvDMMmbdwFWpNNwwwwptjJWn
            RZRZZqvvvDbDHCRTGchHFSGG
            SszgPSPPVltDlqtz
            WfTdTBdQdFnWBBBhBhNjVJtpNsVlDDDHHJWp
            hQhrLFsBwdQPggbRgPwRMg
            frRppMMDMpDnJfprnZhrrhpzWgvvGCvvFzWFvzvVVWFGJB
            TcmLwTsccqwqbPwsdwqdTPSvBvzzztvggVvQCGWQCLBvCv
            sswNjscwmqjwSssjdZNMfHHlHhfrnrgnfR
            JpBJBdmdzZzzpngmbCnlqnNbNM
            MMTHGccLTLvwRMlRnnQnbblnRnSs
            vVGtvMcjLVGHfHDrPPWZppBpJpfZZZ
            FGJtlttPdPtGFldlPRGpJTVzSBSSggHgJjVmBMHjJm
            rhbvqrQLrWqrWLLfqbjjgNmVNSgzTmNgNS
            hsffZQqnqCfZzlPPGlRlcwDs
            HDDdZpcFwHFRFcZqDctpRDHpwTCVwjrBTQTBLBLBJJBjjQTJ
            ldlMzhlPshPbLrrVrQQCMQjB
            glzNfWlvbHqSdNNNcF
            jZCMtnZZHCZwBWMwCwtMmfPFfvHDvzHFLPmFDfvh
            RcrQdRRdGTzGvDGmfgjh
            TsQscdQsQNTNqQQpRrRVCCBMMJJWMMVNVjnNJM
            zVPWhVzLzWBWHZnlqBllqlpRbGNdffscGNdbDRnNSfcG
            MtvSFQQwMcpsGRNGFR
            vvTwJJSgmCSMmjVPPJWWhzllWLVV
            RjdfnJfmbVvVJVFQcs
            rZDZGBBZVvLZLHFW
            qPzTDPlVrjNgfCdmPd
            bcjmQPrnbmVmsLVrLrjmcHGRWlZHHRwHpZRHWWwH
            nFhqzFqJzDJfvfSFqFfGHWZZHGRJRWHZWdpWwZ
            hBCtDSSFCTqCCFzSnzMrLNmrMNPTNMQPMmNL
            qvNBSJVDJGGVSJbVDDVhDbbqPjpWpWzWrnpWvvWPMjnWnpWz
            mlTltwcwMWTPfNTN
            CtCwFmCgmcmlRFmFCtRCHgmDJsbBhVqsbBHVDbNHDHJqqb
            csBFBsLrBGBWcgLcBvRgpRhbwRwlbQwbwQgD
            DCqmDmtTRtRlhdlh
            qnCmTNPmmCnSSzmzNzGLzLccGDBzGrBLvvcW
            FjfBjHnHzPFwhvFFqh
            bjRpGsNsPqQvPclb
            NWGGWGrrZVZjsCLmDMMgzgrSnzSm
            MDgmmsNCmZMWmHCZLrvnLBBjPLVlPVbW
            zcJGQwJdFRnrBVzqzvPr
            hTQwhJwcfTFddFdGSfcRQQGFsggsgsHHnSmgsgsmgCnHNZpC
            BPfwzfsgsvfszvBRbQpttRVpJbJpVg
            LhTmHLbmbcFTFrWCbFqhFHLHVRpVtQpZVVDVprnDMJtJQnVZ
            TGWWbTFFGTqlHhqhSdNdNfNSldjjBfjv
            zCzpWTccHlWcPzMljMttbJfjmlfm
            DqqQVZZqVsqJnbbnmjbJJQ
            ZRmDZsSgVmGLsVqsLDFvrcccHrcTWCgWHBCHcCWp
            cvGlQMtQlPtQWWMlcGsrFwFdbgdbdGGDCDCwdd
            VChVZNBVjTTfhNTFgzrzrJgSdzgzwf
            THThZTqZRHZRqNVZNTVLjRCMmQsntQctMnsPmMmMcWtLMQ
            pNRHrbNlNnRLNpMMMTrcGcGTcccz
            ZttBmsJmZdjsvTTvvdBMjDhfMGWGDfDfcScjfD
            CmtTtwvtCsgllNHPPFbLpC
            NpQcvwwRHvdfRvQsNfBQNvfRhVmVMqsZMmMshjMMtWZtMmrm
            CGHbSSzFLSSHzTnbLnCWMrtWMtjnZMhZrqZtqW
            FzCPPzLbPgFJbHSPldNRpgNfvvccgvwf
            nSjpnnhNchMQZMSScnshshncJCGwHGClwmHPZlJPTVZCwHJf
            LvtzBTgLWgLPlPwHPLPJ
            dTBDqRqFzzhQFhshhNhM
            HjjdPsjnllHsbnnDnbTBzLBFBZLLpRFRcCHRFz
            wqqWwQhQQMCQffqqhtwMGhpZFRRZvzWzFvBvpvmcRvZm
            fGfghtNhthqJrQqMqMMSgDdbPjbssDbdSnjCdd
            cqPwJJnnffBFqSfJFnDDPVplLdglGgLVjzGLdVSzVt
            WHRTWNHsQTNbzsbCbTsvWrWtjlgVdLgLdvdgvmLjpGlgtm
            ZMQrTbNHZNsHHrQCZrNDFzhwnMJcfnDhJPPPFh
            LRCFbjNjbCZDmtmqmRRmLtFJBgWBBpvJMwBJvGjBBvMBgw
            TTrlfHzccVllZhdQgdGMJWvgWgBndwpG
            fVSshSVlsfslhsSHHSZtZZNmNFmtmbFCDF
            SPGCBPDMtbcbCtchSMccDTTrrrTFTrsrMTWHTHFVWF
            JmnzqVmmwwfpJpmdHRTRsdsTrFdrQp
            LqwLgzJgnjqLwgGcVbtjDGjcVbhv
            PQcMvrvMsvmdSPPVccmSJcSpGBWWWbBHfWWnfttJWnWJpJ
            wDzqhjzmqRzDRwqDzNDbWtjWBBBtGbtHpHnnBf
            zglRhDqqDZgRNmZQVCdcCPQvvdZv
            RpVjRgvFjGBNWtBWFDtt
            dcqQwlqMMsCLLfbgQmtD
            snlgzsggTzSTSJTr
            dLHhDdtlMngFcFsFLFzzsj
            vWRGGRVrrWvvGQQJBRsmQzmsqnffqcNfNcfz
            vSRVJBVBwTvWTnHphTgDgtMpDl
            bvvGnnJbfPmfdgJJSVtwwCpTScVfNpSC
            sjsZWDqBqqMRZsDjbWMVwtwNNcNtScRHpRRttp
            hzhDqqWDzZzDZzZLQPJPdPnPvlrbGdlnFQ
            PwWHTwzFvNHsNzmmMwzNWGQrCqCFjpZbpnGqrqnpbr
            gRVRgJRJlDLSJddDccQVrtZnCqjndnrZdnqnqpdq
            chhgSSJfQhRRcSSSSBLVfzmzHTNzMNsTNWHMMvMP
            lftqSpBSvhlDBDlhBSczQGmcFMcMVVFMmGFWsm
            rHLHTNdggsLLnwLHbTTgdrTMPPmMGWZGQQMzQVQFZQGM
            gbJnrHHjnbrgLrRrHpBJvSBDDsfJsDtstq
            dBTtFLTtVmpdLhMprSRSWMRSMR
            QvJvQbjbCgCQRBhzzRsNWNBC
            bjgGqQGbQnjGQgnQgbGgjJnDLHLdfPVtdDmLZdBFVVZttdTf
        """.trimIndent().toLines()
    }

}